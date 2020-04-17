using Project.API.Ordering.Domain.Clients;
using Project.API.Ordering.Domain.Users;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.SharedKernel.Events;

namespace Project.API.Ordering.Events
{
    public sealed class OrderIsCreated : DomainEvent
    {
        public OrderIsCreated(OrderId orderId, Client client, UserId userId) =>
            (OrderId, Client, UserId) = (orderId, client, userId);

        public OrderId OrderId { get; }

        public Client Client { get; }

        public UserId UserId { get; }
    }
}