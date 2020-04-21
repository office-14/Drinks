using System.Threading;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.OrderService
{
    public sealed class OrderNumberProvider
    {
        private volatile int nextId = 0;

        public OrderNumber Generate()
        {
            return new OrderNumber($"ORD-{Interlocked.Increment(ref nextId)}");
        }
    }
}