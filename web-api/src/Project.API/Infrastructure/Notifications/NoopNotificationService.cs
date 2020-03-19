using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Project.API.Ordering.Domain.Clients;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal sealed class NoopNotificationService : INotificationService
    {
        private readonly ILogger<NoopNotificationService> logger;

        public NoopNotificationService(ILogger<NoopNotificationService> logger)
            => this.logger = logger;

        public Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, Client client)
        {
            logger.LogDebug(
                "Simulating sending a notification of order id: {} to device id: {}",
                @event.OrderId.Value,
                client.DeviceId!.Value.Value
                );

            return Task.CompletedTask;
        }
    }
}