using Project.API.Ordering.Domain.Clients;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.SharedKernel.Events;

namespace Project.API.Ordering.Events
{
    public sealed class OrderIsCreated : DomainEvent
    {
        public OrderIsCreated(OrderId orderId, Client client) =>
            (OrderId, Client) = (orderId, client);

        public OrderId OrderId { get; }

        public Client Client { get; }
    }
}