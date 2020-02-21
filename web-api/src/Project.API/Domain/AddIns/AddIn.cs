using System;
using System.Diagnostics.CodeAnalysis;
using Project.API.Domain.Core;

namespace Project.API.Domain.AddIns
{
    public sealed class AddIn : IEquatable<AddIn>
    {
        private AddIn(
            AddInId id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles price
        ) =>
            (Id, Name, Description, PhotoUrl, Price) =
            (id, name, description, photoUrl, price);

        public AddInId Id { get; }

        public Name Name { get; }

        public Description Description { get; }

        public Uri PhotoUrl { get; }

        public Roubles Price { get; }

        public bool Equals([AllowNull] AddIn other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Id.Equals(other.Id);
        }

        public override bool Equals(object? obj) => Equals(obj as AddIn);

        public override int GetHashCode() => Id.GetHashCode();

        public static AddIn Available(
            AddInId id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles price
        ) => new AddIn(id, name, description, photoUrl, price);
    }
}