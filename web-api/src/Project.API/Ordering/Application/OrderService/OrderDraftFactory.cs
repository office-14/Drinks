using System.Threading.Tasks;
using Project.API.Ordering.Application.OrderService.Exceptions;
using Project.API.Ordering.Domain.Drinks;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.OrderService
{
    public sealed class OrderDraftFactory
    {
        private readonly IDrinksRepository drinks;

        private readonly IDrinkSizesRepository drinkSizes;

        private readonly IAddInsRepository addIns;

        public OrderDraftFactory(
            IDrinksRepository drinksRepository,
            IDrinkSizesRepository drinkSizesRepository,
            IAddInsRepository addInsRepository
        ) =>
            (drinks, drinkSizes, addIns) =
            (drinksRepository, drinkSizesRepository, addInsRepository);

        public async Task<OrderDraft> CreateOrderDraftFrom(ClientOrder client)
        {
            if (client.Drinks.Count == 0) throw CannotCreateOrder.BecauseOrderHasNoDrinks();

            var orderDraft = OrderDraft.New();

            foreach (var orderItem in client.Drinks)
            {
                var draftItem = OrderItem.New(
                    await EnsureDrinkExists(orderItem.DrinkId),
                    await EnsureDrinkSizeExists(orderItem.DrinkId, orderItem.DrinkSizeId)
                );

                if (orderItem.AddInIds != null)
                {
                    foreach (var addInId in orderItem.AddInIds)
                    {
                        draftItem.AddAddIn(await EnsureAddInExists(addInId));
                    }
                }

                orderDraft.AddOrderItem(draftItem);
            }

            return orderDraft;
        }

        private async Task<Drink> EnsureDrinkExists(DrinkId drinkId)
        {
            var drinkToOrder = await drinks.DrinkWithId(drinkId);

            return drinkToOrder ?? throw CannotCreateOrder.BecauseDrinkDoesntExist(drinkId);
        }

        private async Task<DrinkSize> EnsureDrinkSizeExists(DrinkId drinkId, DrinkSizeId drinkSizeId)
        {
            var drinkToOrder = await drinkSizes.SizeOfDrink(drinkId, drinkSizeId);

            return drinkToOrder ?? throw CannotCreateOrder.BecauseDrinkOfGivenSizeDoesntExist(drinkId, drinkSizeId);
        }

        private async Task<AddIn> EnsureAddInExists(AddInId addInId)
        {
            var addInToOrder = await addIns.AddInWithId(addInId);

            return addInToOrder ?? throw CannotCreateOrder.BecauseAddInDoesntExist(addInId);
        }
    }
}