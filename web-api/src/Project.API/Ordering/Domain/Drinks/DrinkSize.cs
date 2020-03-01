using System;
using System.Diagnostics.CodeAnalysis;
using Project.API.Ordering.Domain.Core;

namespace Project.API.Ordering.Domain.Drinks
{
    public sealed class DrinkSize : IEquatable<DrinkSize>
    {
        private DrinkSize(
            DrinkSizeId id,
            Name name,
            Volume volume,
            Roubles price
        ) =>
            (Id, Name, Volume, Price) =
            (id, name, volume, price);

        public DrinkSizeId Id { get; }

        public Volume Volume { get; }

        public Name Name { get; }

        public Roubles Price { get; }

        public bool Equals([AllowNull] DrinkSize other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Id.Equals(other.Id);
        }

        public override bool Equals(object? obj) => Equals(obj as DrinkSize);

        public override int GetHashCode() => Id.GetHashCode();

        public static DrinkSize Available(
            DrinkSizeId id,
            Name name,
            Volume volume,
            Roubles price
        ) => new DrinkSize(id, name, volume, price);
    }
}