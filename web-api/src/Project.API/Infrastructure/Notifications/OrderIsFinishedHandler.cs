using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Logging;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal sealed class OrderIsFinishedHandler : INotificationHandler<OrderIsFinished>
    {
        private readonly RegisteredDevices registry;

        private readonly INotificationService notificationService;

        private readonly ILogger<OrderIsFinishedHandler> logger;

        public OrderIsFinishedHandler(
            RegisteredDevices registry,
            INotificationService notificationService,
            ILogger<OrderIsFinishedHandler> logger
        ) =>
            (this.registry, this.notificationService, this.logger) =
            (registry, notificationService, logger);

        public async Task Handle(OrderIsFinished notification, CancellationToken cancellationToken)
        {
            logger.LogInformation("Trying to send notification for order id: {}", notification.OrderId.Value);

            // TODO: it seems better not to directly invoke notification sending
            // but put the notification into the queue and early return (asyncronously).
            // Another process will be in charge of sending notifications by
            // polling the notification queue.
            await this.notificationService.NotifyClientWhenOrderIsFinished(
                notification,
                registry.AllTokens(notification.ClientId)
            );
        }
    }
}