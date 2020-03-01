using System;
using System.Diagnostics.CodeAnalysis;
using Project.API.Ordering.Domain.Core;

namespace Project.API.Ordering.Domain.Drinks
{
    public sealed class Drink : IEquatable<Drink>
    {
        private Drink(
            DrinkId id,
            Name name,
            Description description,
            Uri photoUrl
        ) =>
            (Id, Name, Description, PhotoUrl) =
            (id, name, description, photoUrl);

        public DrinkId Id { get; }

        public Name Name { get; }

        public Description Description { get; }

        public Uri PhotoUrl { get; }

        public bool Equals([AllowNull] Drink other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Id.Equals(other.Id);
        }

        public override bool Equals(object? obj) => Equals(obj as Drink);

        public override int GetHashCode() => Id.GetHashCode();

        public static Drink Available(
            DrinkId id,
            Name name,
            Description description,
            Uri photoUrl
        ) => new Drink(id, name, description, photoUrl);
    }
}