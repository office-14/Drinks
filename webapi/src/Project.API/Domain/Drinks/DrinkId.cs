using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Domain.Drinks
{
    public sealed class DrinkId : IEquatable<DrinkId>
    {
        private DrinkId(int value) => Value = value;

        public int Value { get; }

        public static DrinkId From(int value)
        {
            return new DrinkId(value);
        }

        public bool Equals([AllowNull] DrinkId other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Value.Equals(other.Value);
        }

        public override bool Equals(object? obj) => Equals(obj as DrinkId);

        public override int GetHashCode() => Value.GetHashCode();
    }
}