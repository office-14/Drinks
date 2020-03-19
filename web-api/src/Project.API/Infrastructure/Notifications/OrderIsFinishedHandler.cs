using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using MediatR;
using Microsoft.Extensions.Logging;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal sealed class OrderIsFinishedHandler : INotificationHandler<OrderIsFinished>
    {
        private readonly NotifiableOrders notifyableOrders;

        private readonly INotificationService notificationService;

        private readonly ILogger<OrderIsFinishedHandler> logger;

        public OrderIsFinishedHandler(
            NotifiableOrders notifyableOrders,
            INotificationService notificationService,
            ILogger<OrderIsFinishedHandler> logger
        ) =>
            (this.notifyableOrders, this.notificationService, this.logger) =
            (notifyableOrders, notificationService, logger);

        public async Task Handle(OrderIsFinished notification, CancellationToken cancellationToken)
        {
            logger.LogInformation("Trying to send notification for order id: {}", notification.OrderId.Value);

            var client = notifyableOrders.GetValueOrDefault(notification.OrderId);

            if (client == null)
            {
                logger.LogWarning("There is no client associated with order id: {}", notification.OrderId.Value);
                return;
            }

            if (client.DeviceId.HasValue)
            {
                // TODO: it seems better not to directly invoke notification sending
                // but put the notification into the queue and early return (asyncronously).
                // Another process will be in charge of sending notifications by
                // polling the notification queue.
                await this.notificationService.NotifyClientWhenOrderIsFinished(notification, client);
            }

            notifyableOrders.Remove(notification.OrderId);
        }
    }
}