using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.SharedKernel.Domain.Orders
{
    public readonly struct OrderId : IEquatable<OrderId>
    {
        private OrderId(int value) => Value = value;

        public int Value { get; }

        public bool Equals([AllowNull] OrderId other) => Value.Equals(other.Value);

        public override bool Equals(object? obj) =>
            (obj is OrderId other) && Equals(other);

        public override int GetHashCode() => Value.GetHashCode();

        public static bool operator ==(OrderId left, OrderId right) =>
            left.Equals(right);

        public static bool operator !=(OrderId left, OrderId right) =>
            !left.Equals(right);

        public static OrderId From(int value) => new OrderId(value);
    }
}