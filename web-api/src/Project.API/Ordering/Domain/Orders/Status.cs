using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Ordering.Domain.Orders
{
    public readonly struct Status : IEquatable<Status>
    {
        public static readonly Status Ready = new Status("READY", "Ready");
        public static readonly Status Cooking = new Status("COOKING", "Cooking");

        private Status(string code, string name) => (Code, Name) = (code, name);

        public string Code { get; }

        public string Name { get; }

        public bool Equals([AllowNull] Status other) => (Code, Name) == (other.Code, other.Name);

        public override bool Equals(object? obj) => (obj is Status other) && Equals(other);

        public override int GetHashCode() => HashCode.Combine(Code, Name);

        public static bool operator ==(Status left, Status right) => left.Equals(right);

        public static bool operator !=(Status left, Status right) => !left.Equals(right);
    }
}