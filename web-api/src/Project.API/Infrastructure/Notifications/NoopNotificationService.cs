using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal sealed class NoopNotificationService : INotificationService
    {
        private readonly ILogger<NoopNotificationService> logger;

        public NoopNotificationService(ILogger<NoopNotificationService> logger)
            => this.logger = logger;

        public Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, IEnumerable<DeviceToken> tokens)
        {
            logger.LogDebug(
                "Simulating sending a notification of order id: {}",
                @event.OrderId.Value);

            return Task.CompletedTask;
        }
    }
}