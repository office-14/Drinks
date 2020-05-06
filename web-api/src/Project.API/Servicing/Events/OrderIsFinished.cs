using Project.API.Ordering.Domain.Users;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.SharedKernel.Events;

namespace Project.API.Servicing.Events
{
    public sealed class OrderIsFinished : DomainEvent
    {
        public OrderIsFinished(OrderId orderId, OrderNumber orderNumber, UserId clientId) =>
            (OrderId, OrderNumber, UserId) = (orderId, orderNumber, clientId);

        public OrderId OrderId { get; }

        public OrderNumber OrderNumber { get; }

        public UserId UserId { get; }
    }
}