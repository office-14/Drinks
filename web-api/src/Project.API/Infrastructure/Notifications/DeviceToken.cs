using System;

namespace Project.API.Infrastructure.Notifications
{
    public readonly struct DeviceToken
    {
        private DeviceToken(string value) => Value = value;

        public string Value { get; }

        public static DeviceToken From(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                throw new ArgumentException("Device token cannot be empty string");
            }

            return new DeviceToken(value);
        }
    }
}