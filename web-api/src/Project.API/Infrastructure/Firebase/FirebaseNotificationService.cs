using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FirebaseAdmin.Messaging;
using Microsoft.Extensions.Logging;
using Project.API.Infrastructure.Notifications;
using Project.API.Ordering.Events;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Firebase
{
    internal sealed class FirebaseNotificationService : INotificationService
    {
        private readonly ILogger<FirebaseNotificationService> logger;

        public FirebaseNotificationService(ILogger<FirebaseNotificationService> logger)
            => this.logger = logger;

        public async Task NotifyClientWhenOrderIsCreated(OrderIsCreated @event, IEnumerable<DeviceToken> tokens)
        {
            await SendNotificationsToTokens(tokens, token => new Message
            {
                Token = token.Value,

                Notification = new Notification
                {
                    Title = "Изменение статуса заказа",
                    Body = $"Ваш заказ {@event.OrderNumber.Value} принят"
                },

                Data = new Dictionary<string, string>
                {
                    { "event_type", "order_is_created" }
                }
            });
        }

        public async Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, IEnumerable<DeviceToken> tokens)
        {
            await SendNotificationsToTokens(tokens, token => new Message
            {
                Token = token.Value,

                Notification = new Notification
                {
                    Title = "Изменение статуса заказа",
                    Body = $"Ваш заказ {@event.OrderNumber.Value} завершен"
                },

                Data = new Dictionary<string, string>
                {
                    { "event_type", "order_is_finished" }
                }
            });
        }

        private async Task SendNotificationsToTokens(
            IEnumerable<DeviceToken> tokens,
            Func<DeviceToken, Message> messageFactory
        )
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
                    .Select(messageFactory));
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