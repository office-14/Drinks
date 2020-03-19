using System;
using System.Threading.Tasks;
using FirebaseAdmin.Messaging;
using Microsoft.Extensions.Logging;
using Project.API.Infrastructure.Notifications;
using Project.API.Ordering.Domain.Clients;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Firebase
{
    internal sealed class FirebaseNotificationService : INotificationService
    {
        private readonly ILogger<FirebaseNotificationService> logger;

        public FirebaseNotificationService(ILogger<FirebaseNotificationService> logger)
            => this.logger = logger;

        public async Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, Client client)
        {
            var messaging = FirebaseMessaging.DefaultInstance;
            var deviceId = client.DeviceId!.Value.Value;

            logger.LogInformation("Trying to send a notification to device id: {}", deviceId);

            try
            {
                await messaging.SendAsync(new Message
                {
                    Token = deviceId,

                    Notification = new Notification
                    {
                        Title = "Order status change",
                        Body = "Your order is finished"
                    }
                });
            }
            catch (Exception ex)
            {
                logger.LogError(
                    ex,
                    "Cannot deliver notification message for device id: {}",
                    deviceId);
            }
        }
    }
}