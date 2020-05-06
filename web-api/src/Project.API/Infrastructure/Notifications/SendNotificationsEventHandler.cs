using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Logging;
using Project.API.Ordering.Events;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal sealed class SendNotificationsEventHandler :
        INotificationHandler<OrderIsFinished>,
        INotificationHandler<OrderIsCreated>
    {
        private readonly RegisteredDevices registry;

        private readonly INotificationService notificationService;

        private readonly ILogger<SendNotificationsEventHandler> logger;

        public SendNotificationsEventHandler(
            RegisteredDevices registry,
            INotificationService notificationService,
            ILogger<SendNotificationsEventHandler> logger
        ) =>
            (this.registry, this.notificationService, this.logger) =
            (registry, notificationService, logger);

        public async Task Handle(OrderIsFinished notification, CancellationToken cancellationToken)
        {
            logger.LogInformation("Trying to notify user id '{}' about finished order id '{}'",
                notification.UserId.Value,
                notification.OrderId.Value);

            // TODO: it seems better not to directly invoke notification sending
            // but put the notification into the queue and early return (asyncronously).
            // Another process will be in charge of sending notifications by
            // polling the notification queue.
            await this.notificationService.NotifyClientWhenOrderIsFinished(
                notification,
                registry.AllTokens(notification.UserId)
            );
        }

        public async Task Handle(OrderIsCreated notification, CancellationToken cancellationToken)
        {
            logger.LogInformation("Trying to notify user id '{}' about created order id '{}'",
                notification.UserId.Value,
                notification.OrderId.Value);

            await this.notificationService.NotifyClientWhenOrderIsCreated(
                notification,
                registry.AllTokens(notification.UserId)
            );
        }
    }
}