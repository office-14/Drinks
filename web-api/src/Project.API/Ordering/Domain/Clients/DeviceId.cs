using System;

namespace Project.API.Ordering.Domain.Clients
{
    public readonly struct DeviceId
    {
        private DeviceId(string value) => Value = value;

        public string Value { get; }

        public static DeviceId From(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                throw new ArgumentException("Device identifier cannot be empty string");
            }

            return new DeviceId(value);
        }
    }
}