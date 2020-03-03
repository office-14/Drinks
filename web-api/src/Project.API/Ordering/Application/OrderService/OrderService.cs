using System.Threading.Tasks;
using Project.API.Ordering.Application.OrderService.Exceptions;
using Project.API.Ordering.Domain.Drinks;
using Project.API.Ordering.Domain.Orders;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.OrderService
{
    public sealed class OrderService
    {
        private readonly IDrinksRepository drinks;
        private readonly IDrinkSizesRepository drinkSizes;
        private readonly IAddInsRepository addIns;
        private readonly IOrdersRepository orders;
        private readonly OrderNumberProvider orderNumbers;

        public OrderService(
            IDrinksRepository drinksRepository,
            IDrinkSizesRepository drinkSizesRepository,
            IAddInsRepository addInsRepository,
            IOrdersRepository ordersRepository,
            OrderNumberProvider orderNumberProvider
        ) =>
            (drinks, drinkSizes, addIns, orders, orderNumbers) =
            (drinksRepository, drinkSizesRepository, addInsRepository,
            ordersRepository, orderNumberProvider);

        public async Task<OrderId> CreateNewOrder(ClientOrder client)
        {
            if (client.Drinks.Count == 0) throw CannotCreateOrder.BecauseOrderHasNoDrinks();

            var orderDraft = OrderDraft.New();
            foreach (var orderItem in client.Drinks)
            {
                var drinkToOrder = await drinks.DrinkWithId(orderItem.DrinkId);
                if (drinkToOrder == null) throw CannotCreateOrder.BecauseDrinkDoesntExist(orderItem.DrinkId);

                var drinkSizeToOrder = await drinkSizes.SizeOfDrink(orderItem.DrinkId, orderItem.DrinkSizeId);
                if (drinkSizeToOrder == null) throw CannotCreateOrder.BecauseDrinkOfGivenSizeDoesntExist(orderItem.DrinkId, orderItem.DrinkSizeId);

                var draftItem = OrderItem.New(drinkToOrder, drinkSizeToOrder);

                if (orderItem.AddInIds != null)
                {
                    foreach (var addInId in orderItem.AddInIds)
                    {
                        var addInToOrder = await addIns.AddInWithId(addInId);
                        if (addInToOrder == null) throw CannotCreateOrder.BecauseAddInDoesntExist(addInId);

                        draftItem.AddAddIn(addInToOrder);
                    }
                }

                orderDraft.AddOrderItem(draftItem);
            }

            var persistedOrder = await orders.Save(Order.New(
                orderNumbers.Generate(),
                orderDraft.TotalPrice(),
                orderDraft
            ));

            return persistedOrder.Id;
        }
    }
}