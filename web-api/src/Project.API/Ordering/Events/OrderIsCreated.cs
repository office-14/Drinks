using Project.API.Ordering.Domain.Users;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.SharedKernel.Events;

namespace Project.API.Ordering.Events
{
    public sealed class OrderIsCreated : DomainEvent
    {
        public OrderIsCreated(OrderId orderId, UserId userId) =>
            (OrderId, UserId) = (orderId, userId);

        public OrderId OrderId { get; }

        public UserId UserId { get; }
    }
}