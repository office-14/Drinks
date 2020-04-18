using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Infrastructure.Notifications
{
    public readonly struct DeviceId : IEquatable<DeviceId>
    {
        private DeviceId(string value) => Value = value;

        public string Value { get; }

        public bool Equals([AllowNull] DeviceId other) =>
            Value.Equals(other.Value);

        public override bool Equals(object? obj) =>
            (obj is DeviceId other) && Equals(other);

        public override int GetHashCode() => Value.GetHashCode();

        public static bool operator ==(DeviceId left, DeviceId right) =>
            left.Equals(right);

        public static bool operator !=(DeviceId left, DeviceId right) =>
            !left.Equals(right);

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