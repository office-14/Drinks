using Project.API.SharedKernel.Domain.Orders;
using Project.API.SharedKernel.Events;

namespace Project.API.Servicing.Events
{
    public sealed class OrderIsFinished : DomainEvent
    {
        public OrderIsFinished(OrderId orderId) => OrderId = orderId;

        public OrderId OrderId { get; }
    }
}