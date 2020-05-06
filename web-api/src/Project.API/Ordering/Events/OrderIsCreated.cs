using Project.API.Ordering.Domain.Users;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.SharedKernel.Events;

namespace Project.API.Ordering.Events
{
    public sealed class OrderIsCreated : DomainEvent
    {
        public OrderIsCreated(OrderId orderId, OrderNumber orderNumber, UserId userId) =>
            (OrderId, OrderNumber, UserId) = (orderId, orderNumber, userId);

        public OrderId OrderId { get; }

        public OrderNumber OrderNumber { get; }

        public UserId UserId { get; }
    }
}