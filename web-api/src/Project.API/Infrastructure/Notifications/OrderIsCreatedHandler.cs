using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Project.API.Ordering.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal sealed class OrderIsCreatedHandler : INotificationHandler<OrderIsCreated>
    {
        private readonly NotifiableOrders notifyableOrders;

        public OrderIsCreatedHandler(NotifiableOrders notifyableOrders)
            => this.notifyableOrders = notifyableOrders;

        public Task Handle(OrderIsCreated notification, CancellationToken cancellationToken)
        {
            lock (notifyableOrders)
            {
                notifyableOrders[notification.OrderId] = notification.Client;
            }

            return Task.CompletedTask;
        }
    }
}