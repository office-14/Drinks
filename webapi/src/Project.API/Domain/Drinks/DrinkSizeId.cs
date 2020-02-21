using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Domain.Drinks
{
    public sealed class DrinkSizeId : IEquatable<DrinkSizeId>
    {
        private DrinkSizeId(int value) => Value = value;

        public int Value { get; }

        public bool Equals([AllowNull] DrinkSizeId other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Value.Equals(other.Value);
        }

        public override bool Equals(object? obj) => Equals(obj as DrinkSizeId);

        public override int GetHashCode() => Value.GetHashCode();

        public static DrinkSizeId From(int value) => new DrinkSizeId(value);
    }
}