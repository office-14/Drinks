using Project.API.Ordering.Domain.Users;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.SharedKernel.Events;

namespace Project.API.Servicing.Events
{
    public sealed class OrderIsFinished : DomainEvent
    {
        public OrderIsFinished(OrderId orderId, UserId clientId) =>
            (OrderId, ClientId) = (orderId, clientId);

        public OrderId OrderId { get; }

        public UserId ClientId { get; }
    }
}