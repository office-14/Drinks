using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Project.API.Application.OrderDetails;
using Project.API.Domain.Orders;

namespace Project.API.Infrastructure.Repositories
{
    public sealed class InMemoryOrdersRepository : IOrderDetailsRepository, IOrdersRepository
    {
        private static int IdCounter = 0;

        private readonly Dictionary<int, OrderDetails> orders =
            new Dictionary<int, OrderDetails>();

        private readonly object syncLock = new object();

        public Task<OrderDetails> GetOrderDetailsById(int orderId, CancellationToken token = default)
        {
            lock (syncLock)
            {
                return Task.FromResult(orders.GetValueOrDefault(orderId));
            }
        }

        public Task<Order> OrderWithId(int orderId, CancellationToken token = default)
        {
            lock (syncLock)
            {
                var orderDetails = orders.GetValueOrDefault(orderId);

                if (orderDetails == null) return Task.FromResult((Order)null);

                return Task.FromResult(Order.Existing(
                    orderDetails.Id,
                    orderDetails.OrderNumber,
                    orderDetails.TotalPrice,
                    orderDetails.Status
                ));
            }
        }

        public Task<Order> Save(Order order, CancellationToken token = default)
        {
            var persistedOrder = order.Id == default(int)
                ? CreateNewOrder(order)
                : UpdateExistingOrder(order);

            return Task.FromResult(persistedOrder);
        }

        private Order CreateNewOrder(Order order)
        {
            var nextId = Interlocked.Increment(ref IdCounter);

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
    }
}