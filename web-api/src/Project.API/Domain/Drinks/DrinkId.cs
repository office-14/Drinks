using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Domain.Drinks
{
    public readonly struct DrinkId : IEquatable<DrinkId>
    {
        private DrinkId(int value) => Value = value;

        public int Value { get; }

        public bool Equals([AllowNull] DrinkId other) =>
            Value.Equals(other.Value);

        public override bool Equals(object? obj) =>
            (obj is DrinkId other) && Equals(other);

        public override int GetHashCode() => Value.GetHashCode();

        public static bool operator ==(DrinkId left, DrinkId right) =>
            left.Equals(right);

        public static bool operator !=(DrinkId left, DrinkId right) =>
            !left.Equals(right);

        public static DrinkId From(int value) => new DrinkId(value);
    }
}