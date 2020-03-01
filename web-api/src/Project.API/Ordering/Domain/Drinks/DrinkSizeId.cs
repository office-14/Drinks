using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Ordering.Domain.Drinks
{
    public readonly struct DrinkSizeId : IEquatable<DrinkSizeId>
    {
        private DrinkSizeId(int value) => Value = value;

        public int Value { get; }

        public bool Equals([AllowNull] DrinkSizeId other) =>
            Value.Equals(other.Value);

        public override bool Equals(object? obj) =>
            (obj is DrinkSizeId other) && Equals(other);

        public override int GetHashCode() => Value.GetHashCode();

        public static bool operator ==(DrinkSizeId left, DrinkSizeId right) =>
            left.Equals(right);

        public static bool operator !=(DrinkSizeId left, DrinkSizeId right) =>
            !left.Equals(right);

        public static DrinkSizeId From(int value) => new DrinkSizeId(value);
    }
}