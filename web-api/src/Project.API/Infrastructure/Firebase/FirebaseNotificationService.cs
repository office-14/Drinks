using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FirebaseAdmin.Messaging;
using Microsoft.Extensions.Logging;
using Project.API.Infrastructure.Notifications;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Firebase
{
    internal sealed class FirebaseNotificationService : INotificationService
    {
        private readonly ILogger<FirebaseNotificationService> logger;

        public FirebaseNotificationService(ILogger<FirebaseNotificationService> logger)
            => this.logger = logger;

        public async Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, IEnumerable<DeviceToken> tokens)
        {
            var messaging = FirebaseMessaging.DefaultInstance;

            try
            {
                await messaging.SendAllAsync(tokens.Select(token => new Message
                {
                    Token = token.Value,

                    Notification = new Notification
                    {
                        Title = "Order status change",
                        Body = "Your order is finished"
                    }
                }));
            }
            catch (Exception ex)
            {
                logger.LogError(
                    ex,
                    "Cannot deliver notification message");
            }
        }
    }
}