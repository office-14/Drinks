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
            var tokensList = tokens.ToList();

            if (tokensList.Count == 0)
            {
                logger.LogInformation(
                    "There are no registered device tokens. No notifications will be sent."
                );
                return;
            }

            logger.LogInformation("Have '{}' devices to send notifications to.", tokensList.Count);

            try
            {
                await FirebaseMessaging.DefaultInstance.SendAllAsync(tokensList
                    .Select(token => new Message
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