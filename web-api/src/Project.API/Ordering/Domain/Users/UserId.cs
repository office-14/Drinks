using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Ordering.Domain.Users
{
    public readonly struct UserId : IEquatable<UserId>
    {
        private UserId(string value) => Value = value;

        public string Value { get; }

        public static UserId From(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                throw new ArgumentException("User id cannot be empty string.");
            }

            return new UserId(value);
        }

        public bool Equals([AllowNull] UserId other) =>
            Value.Equals(other.Value);

        public override bool Equals(object? obj) =>
            (obj is UserId other) && Equals(other);

        public override int GetHashCode() => Value.GetHashCode();

        public static bool operator ==(UserId left, UserId right) =>
            left.Equals(right);

        public static bool operator !=(UserId left, UserId right) =>
            !left.Equals(right);
    }
}