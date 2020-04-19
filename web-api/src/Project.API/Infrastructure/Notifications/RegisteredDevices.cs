using System.Collections.Generic;
using System.Linq;
using Microsoft.Extensions.Logging;
using Project.API.Ordering.Domain.Users;

namespace Project.API.Infrastructure.Notifications
{
    public sealed class RegisteredDevices
    {
        private readonly Dictionary<UserId, Dictionary<DeviceId, DeviceToken>> registry =
            new Dictionary<UserId, Dictionary<DeviceId, DeviceToken>>();

        private readonly ILogger<RegisteredDevices> logger;

        private readonly object syncObject = new object();

        public RegisteredDevices(ILogger<RegisteredDevices> logger)
        {
            this.logger = logger;
        }

        public void RemoveToken(UserId user, DeviceId device)
        {
            lock (syncObject)
            {
                if (!registry.ContainsKey(user)) return;

                var devices = registry[user];

                devices.Remove(device);

                logger.LogInformation("Device token for device id '{}' was deleted.", device.Value);
            }
        }

        public void UpdateToken(UserId user, DeviceId device, DeviceToken newToken)
        {
            lock (syncObject)
            {
                if (!registry.ContainsKey(user))
                {
                    registry[user] = new Dictionary<DeviceId, DeviceToken>();
                }

                registry[user][device] = newToken;

                logger.LogInformation(
                    "Device token was updated to token '{}' for device id '{}'.",
                    newToken.Value,
                    device.Value
                );
            }
        }

        public IEnumerable<DeviceToken> AllTokens(UserId user)
        {
            lock (syncObject)
            {
                if (!registry.ContainsKey(user)) return Enumerable.Empty<DeviceToken>();

                return registry[user].Values.ToList();
            }
        }
    }
}