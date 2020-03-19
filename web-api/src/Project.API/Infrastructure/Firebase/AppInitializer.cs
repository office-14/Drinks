using System;
using FirebaseAdmin;
using Microsoft.Extensions.Logging;

namespace Project.API.Infrastructure.Firebase
{
    internal static class AppInitializer
    {
        public static bool InitializeDefaultApp(ILogger logger)
        {
            logger.LogInformation("Creating default Firebase app");

            try
            {
                FirebaseApp.Create();

                return true;
            }
            catch (Exception ex) // TODO: how about FirebaseMessagingException?
            {
                logger.LogError(
                    ex,
                    "Cannot create default Firebase app"
                );

                return false;
            }
        }
    }
}