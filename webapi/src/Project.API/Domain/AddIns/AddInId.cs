using System;
using System.Diagnostics.CodeAnalysis;

namespace Project.API.Domain.AddIns
{
    public sealed class AddInId : IEquatable<AddInId>
    {
        private AddInId(int value) => Value = value;

        public int Value { get; }

        public bool Equals([AllowNull] AddInId other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Value.Equals(other.Value);
        }

        public override bool Equals(object? obj) => Equals(obj as AddInId);

        public override int GetHashCode() => Value.GetHashCode();

        public static AddInId From(int value) => new AddInId(value);
    }
}