using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Domain.Orders
{
    public sealed class OrderId : IEquatable<OrderId>
    {
        private OrderId(int value) => Value = value;

        public int Value { get; }

        public static OrderId From(string value)
        {
            if (int.TryParse(value, out int intValue))
            {
                return new OrderId(intValue);
            }

            throw new ArgumentException($"Invalid order id value='{value}'. Order identified must be number");
        }

        public static OrderId From(int value) => new OrderId(value);


        public bool Equals([AllowNull] OrderId other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Value.Equals(other.Value);
        }

        public override bool Equals(object? obj) => Equals(obj as OrderId);

        public override int GetHashCode() => Value.GetHashCode();
    }
}