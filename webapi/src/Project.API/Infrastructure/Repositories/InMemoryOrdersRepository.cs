using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Project.API.Application.OrderDetails;

namespace Project.API.Infrastructure.Repositories
{
    public sealed class InMemoryOrdersRepository : IOrderDetailsRepository
    {
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
    }
}