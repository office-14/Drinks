using System;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Project.API.Infrastructure.Notifications;

namespace Project.API.Infrastructure.Firebase
{
    internal static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddFirebaseNotifications(this IServiceCollection services)
        {
            services.AddSingleton<FirebaseNotificationService>();
            services.AddSingleton<NoopNotificationService>();
            services.AddSingleton<INotificationService>(NotificationServiceFactory);

            return services;
        }
        private static INotificationService NotificationServiceFactory(IServiceProvider services)
        {
            var loggerFactory = services.GetRequiredService<ILoggerFactory>();
            var logger = loggerFactory.CreateLogger("Firebase notifications");

            if (AppInitializer.InitializeDefaultApp(logger))
            {
                return services.GetRequiredService<FirebaseNotificationService>();
            }

            return services.GetRequiredService<NoopNotificationService>();
        }
    }
}