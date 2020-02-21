using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Domain.AddIns
{
    public readonly struct AddInId : IEquatable<AddInId>
    {
        private AddInId(int value) => Value = value;

        public int Value { get; }

        public bool Equals([AllowNull] AddInId other) =>
            Value.Equals(other.Value);

        public override bool Equals(object? obj) =>
            (obj is AddInId other) && Equals(other);

        public override int GetHashCode() => Value.GetHashCode();

        public static bool operator ==(AddInId left, AddInId right) =>
            left.Equals(right);

        public static bool operator !=(AddInId left, AddInId right) =>
            !left.Equals(right);

        public static AddInId From(int value) => new AddInId(value);
    }
}