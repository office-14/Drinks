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

        public Task<Order> Save(Order order, CancellationToken token = default)
        {
            var nextId = Interlocked.Increment(ref IdCounter);

            var orderDetails = OrderDetails.Available(
                nextId,
                order.OrderNumber,
                order.TotalPrice,
                order.Status,
                order.CreatedDate,
                order.FinishDate
            );

            lock (syncLock)
            {
                orders.Add(nextId, orderDetails);
            }

            var persistedOrder = Order.Existing(
                nextId,
                order.OrderNumber,
                order.TotalPrice,
                order.Status,
                order.CreatedDate,
                order.FinishDate
            );

            return Task.FromResult(persistedOrder);
        }
    }
}