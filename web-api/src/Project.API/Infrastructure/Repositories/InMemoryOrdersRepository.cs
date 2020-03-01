using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Project.API.Ordering.Application.OrderDetails;
using Project.API.Ordering.Domain.Orders;
using Project.API.Servicing.Application.BookedOrders;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Infrastructure.Repositories
{
    public sealed class InMemoryOrdersRepository
        : IOrderDetailsRepository, IOrdersRepository, IBookedOrdersRepository
    {
        private int IdCounter = 0;

        private readonly Dictionary<OrderId, OrderDetails> orders =
            new Dictionary<OrderId, OrderDetails>();

        private readonly object syncLock = new object();

        public Task<OrderDetails?> OrderDetailsWithId(OrderId id, CancellationToken token = default)
        {
            lock (syncLock)
            {
                return Task.FromResult<OrderDetails?>(orders.GetValueOrDefault(id));
            }
        }

        public Task<Order?> OrderWithId(OrderId id, CancellationToken token = default)
        {
            lock (syncLock)
            {
                var orderDetails = orders.GetValueOrDefault(id);

                if (orderDetails == null) return Task.FromResult<Order?>(null);

                return Task.FromResult<Order?>(Order.Existing(
                    orderDetails.Id,
                    orderDetails.OrderNumber,
                    orderDetails.TotalPrice,
                    orderDetails.Status
                ));
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

            var orderDetails = OrderDetails.Available(
                nextId,
                order.OrderNumber,
                order.TotalPrice,
                order.Status
            );

            lock (syncLock)
            {
                orders.Add(nextId, orderDetails);
            }

            var persistedOrder = Order.Existing(
                nextId,
                order.OrderNumber,
                order.TotalPrice,
                order.Status
            );

            return persistedOrder;
        }

        private Order UpdateExistingOrder(Order order)
        {
            var orderDetails = OrderDetails.Available(
                order.Id,
                order.OrderNumber,
                order.TotalPrice,
                order.Status
            );

            lock (syncLock)
            {
                orders[order.Id] = orderDetails;
            }

            return order;
        }

        public Task<IEnumerable<BookedOrder>> BookedOrders(CancellationToken token = default)
        {
            lock (syncLock)
            {
                return Task.FromResult(orders
                    .Where(o => o.Value.Status.Equals(Status.Cooking))
                    .Select(o =>
                    {
                        var order = o.Value;
                        return BookedOrder.Available(
                            order.Id,
                            order.OrderNumber,
                            order.TotalPrice
                        );
                    }));
            }
        }
    }
}