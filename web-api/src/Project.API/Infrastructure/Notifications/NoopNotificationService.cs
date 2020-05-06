using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Project.API.Ordering.Events;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    public sealed class NoopNotificationService : INotificationService
    {
        private readonly ILogger<NoopNotificationService> logger;

        public NoopNotificationService(ILogger<NoopNotificationService> logger)
            => this.logger = logger;

        public Task NotifyClientWhenOrderIsCreated(OrderIsCreated @event, IEnumerable<DeviceToken> tokens)
        {
            logger.LogDebug(
                "Simulating sending a notification to user id: {} about created order id: {}",
                @event.UserId.Value,
                @event.OrderId.Value);

            return Task.CompletedTask;
        }

        public Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, IEnumerable<DeviceToken> tokens)
        {
            logger.LogDebug(
                "Simulating sending a notification to user id: {} about finished order id: {}",
                @event.UserId.Value,
                @event.OrderId.Value);

            return Task.CompletedTask;
        }
    }
}