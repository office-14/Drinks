using System;
using System.Threading.Tasks;
using Project.API.Application.OrderService.Exceptions;
using Project.API.Domain.AddIns;
using Project.API.Domain.Drinks;
using Project.API.Domain.Orders;

namespace Project.API.Application.OrderService
{
    public sealed class OrderService
    {
        private readonly IDrinksRepository drinksRepository;
        private readonly IDrinkSizesRepository drinkSizesRepository;
        private readonly IAddInsRepository addInsRepository;
        private readonly IOrdersRepository ordersRepository;
        private readonly OrderNumberProvider orderNumberProvider;

        public OrderService(
            IDrinksRepository drinksRepository,
            IDrinkSizesRepository drinkSizesRepository,
            IAddInsRepository addInsRepository,
            IOrdersRepository ordersRepository,
            OrderNumberProvider orderNumberProvider
        )
        {
            this.drinksRepository = drinksRepository;
            this.drinkSizesRepository = drinkSizesRepository;
            this.addInsRepository = addInsRepository;
            this.ordersRepository = ordersRepository;
            this.orderNumberProvider = orderNumberProvider;
        }

        public async Task<int> CreateNewOrder(ClientOrder client)
        {
            if (client.Drinks.Count == 0) throw CannotCreateOrder.BecauseOrderHasNoDrinks();

            var orderDraft = OrderDraft.New();
            foreach (var orderItem in client.Drinks)
            {
                var drinkToOrder = await drinksRepository.DrinkWithId(orderItem.DrinkId);
                if (drinkToOrder == null) throw CannotCreateOrder.BecauseDrinkDoesntExist(orderItem.DrinkId);

                var drinkSizeToOrder = await drinkSizesRepository.SizeOfDrink(orderItem.DrinkId, orderItem.DrinkSizeId);
                if (drinkSizeToOrder == null) throw CannotCreateOrder.BecauseDrinkOfGivenSizeDoesntExist(orderItem.DrinkId, orderItem.DrinkSizeId);

                var draftItem = OrderItem.New(drinkToOrder, drinkSizeToOrder);

                if (orderItem.AddInIds != null)
                {
                    foreach (var addInId in orderItem.AddInIds)
                    {
                        var addInToOrder = await addInsRepository.AddInWithId(addInId);
                        if (addInToOrder == null) throw CannotCreateOrder.BecauseAddInDoesntExist(addInId);

                        draftItem.AddAddIn(addInToOrder);
                    }
                }

                orderDraft.AddOrderItem(draftItem);
            }

            var persistedOrder = await ordersRepository.Save(Order.New(
                orderNumberProvider.Generate(),
                orderDraft.TotalPrice()
            ));

            return persistedOrder.Id;
        }

        public async Task FinishOrder(int orderId)
        {
            var order = await ordersRepository.OrderWithId(orderId);

            if (order == null) return;

            order.Finish();

            await ordersRepository.Save(order);
        }
    }
}