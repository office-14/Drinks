using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Project.API.Ordering.Domain.Orders;
using Project.API.Servicing.Application.BookedOrders;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Infrastructure.Repositories
{
    public sealed class InMemoryOrdersRepository
        : IOrdersRepository, IBookedOrdersRepository
    {
        private volatile int IdCounter = 0;

        private readonly Dictionary<OrderId, Order> orders =
            new Dictionary<OrderId, Order>();

        private readonly object syncLock = new object();

        public Task<Order?> OrderWithId(OrderId id, CancellationToken token = default)
        {
            lock (syncLock)
            {
                return Task.FromResult<Order?>(orders.GetValueOrDefault(id));
            }
        }

        public Task<Order> Save(Order order, CancellationToken token = default)
        {
            var persistedOrder = order.Id == default
                ? CreateNewOrder(order)
                : UpdateExistingOrder(order);

            return Task.FromResult(persistedOrder);
        }

        private Order CreateNewOrder(Order order)
        {
            var nextId = OrderId.From(Interlocked.Increment(ref IdCounter));

            var newOrder = Order.Existing(
                nextId,
                order.OrderNumber,
                order.TotalPrice,
                order.Status,
                order.Draft,
                order.ClientId,
                order.Comment
            );

            lock (syncLock)
            {
                orders[nextId] = newOrder;
            }

            var persistedOrder = Order.Existing(
                nextId,
                newOrder.OrderNumber,
                newOrder.TotalPrice,
                newOrder.Status,
                newOrder.Draft,
                newOrder.ClientId,
                newOrder.Comment
            );

            return persistedOrder;
        }

        private Order UpdateExistingOrder(Order order)
        {
            var updatedOrder = Order.Existing(
                order.Id,
                order.OrderNumber,
                order.TotalPrice,
                order.Status,
                order.Draft,
                order.ClientId,
                order.Comment
            );

            lock (syncLock)
            {
                orders[order.Id] = updatedOrder;
            }

            return order;
        }

        public Task<IEnumerable<BookedOrder>> BookedOrders(CancellationToken token = default)
        {
            lock (syncLock)
            {
                return Task.FromResult<IEnumerable<BookedOrder>>(orders.Values
                    .Where(o => o.Status.Equals(Status.Cooking))
                    .Select(ToBookedOrder)
                    .OrderBy(o => o.Id.Value));
            }
        }

        private static BookedOrder ToBookedOrder(Order order) =>
            BookedOrder.Available(
                order.Id,
                order.OrderNumber,
                order.TotalPrice,
                order.Comment,
                order.Draft.Items.Select(ToBookedItem).ToArray()
            );

        private static BookedItem ToBookedItem(OrderItem item) =>
            BookedItem.Ordered(
                item.Drink.Name,
                item.Size.Volume,
                item.AddIns.Select(a => a.Name).ToArray(),
                item.Count
            );
    }
}