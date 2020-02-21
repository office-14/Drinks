using System.Threading;
using Project.API.Domain.Orders;

namespace Project.API.Application
{
    public sealed class OrderNumberProvider
    {
        private int nextId = 0;

        public OrderNumber Generate()
        {
            return new OrderNumber($"ORD-{Interlocked.Increment(ref nextId)}");
        }
    }
}