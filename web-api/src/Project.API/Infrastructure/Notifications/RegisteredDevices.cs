using System.Collections.Generic;
using System.Linq;
using Project.API.Ordering.Domain.Users;

namespace Project.API.Infrastructure.Notifications
{
    public sealed class RegisteredDevices
    {
        private readonly Dictionary<UserId, Dictionary<DeviceId, DeviceToken>> registry =
            new Dictionary<UserId, Dictionary<DeviceId, DeviceToken>>();

        public void RemoveToken(UserId user, DeviceId device)
        {
            if (!registry.ContainsKey(user)) return;

            var devices = registry[user];

            devices.Remove(device);
        }

        public void UpdateToken(UserId user, DeviceId device, DeviceToken newToken)
        {
            if (!registry.ContainsKey(user)) return;

            registry[user][device] = newToken;
        }

        public IEnumerable<DeviceToken> AllTokens(UserId user)
        {
            if (!registry.ContainsKey(user)) return Enumerable.Empty<DeviceToken>();

            return registry[user].Values.ToList();
        }
    }
}