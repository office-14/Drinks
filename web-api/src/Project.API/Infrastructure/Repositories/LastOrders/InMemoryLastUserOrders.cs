using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Project.API.Ordering.Application.LastUserOrder;
using Project.API.Ordering.Application.LastUserOrderStatus;
using Project.API.Ordering.Domain.Drinks;
using Project.API.Ordering.Domain.Orders;
using Project.API.Ordering.Domain.Users;
using Project.API.Ordering.Events;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Infrastructure.Repositories.LastOrders
{
    internal sealed class InMemoryLastUserOrders :
        ILastUserOrderProvider,
        ILastUserOrderStatusProvider,
        INotificationHandler<OrderIsCreated>
    {
        private readonly LastUserOrders orders;

        private readonly IOrdersRepository ordersRepository;

        public InMemoryLastUserOrders(
            LastUserOrders orders,
            IOrdersRepository ordersRepository
        )
        {
            this.orders = orders;
            this.ordersRepository = ordersRepository;
        }

        public Task Handle(OrderIsCreated notification, CancellationToken cancellationToken)
        {
            lock (orders)
            {
                orders[notification.UserId] = notification.OrderId;
            }

            return Task.CompletedTask;
        }

        public async Task<LastOrderDetails?> LastUserOrder(User user, CancellationToken token = default)
        {
            var order = await LastDomainOrderOfUser(user);

            if (order == null) return null;

            return LastOrderDetails.Available(
                order.Id,
                order.Created,
                order.OrderNumber,
                order.TotalPrice,
                order.Status,
                order.Comment,
                ToLastOrderDrinks(order.Draft.Items)
            );
        }

        private List<LastOrderDrink> ToLastOrderDrinks(IEnumerable<OrderItem> items)
        {
            return items.Select(ToLastOrderDrink).ToList();
        }

        private LastOrderDrink ToLastOrderDrink(OrderItem item)
        {
            return LastOrderDrink.Available(
                ToLastDrinkDetails(item.Drink),
                ToLastDrinkSizeDetails(item.Size),
                item.AddIns.Select(ToLastDrinkAddInDetails).ToList(),
                item.Count,
                item.TotalPrice()
            );
        }

        private DrinkDetails ToLastDrinkDetails(Drink drink)
        {
            return DrinkDetails.Ordered(drink.Id, drink.Name, drink.PhotoUrl);
        }

        private DrinkSizeDetails ToLastDrinkSizeDetails(DrinkSize size)
        {
            return DrinkSizeDetails.Ordered(size.Id, size.Volume, size.Name);
        }

        private AddInDetails ToLastDrinkAddInDetails(AddIn addIn)
        {
            return AddInDetails.Ordered(addIn.Id, addIn.Name);
        }

        public async Task<bool> UserHasUnfinishedOrder(User user, CancellationToken token = default)
        {
            var lastOrder = await this.LastDomainOrderOfUser(user);

            if (lastOrder == null) return false;

            return !lastOrder.IsFinished();
        }

        private async Task<Order?> LastDomainOrderOfUser(User user)
        {
            bool valueExists;
            OrderId orderId;

            lock (orders)
            {
                valueExists = orders.TryGetValue(user.Id, out orderId);
            }

            if (!valueExists) return null;

            return await ordersRepository.OrderWithId(orderId);
        }

        public async Task<LastOrderStatusDetails?> StatusOfLastOrder(User user, CancellationToken token = default)
        {
            var order = await LastDomainOrderOfUser(user);

            if (order == null) return null;

            return LastOrderStatusDetails.Current(order.Id, order.Status);
        }
    }
}