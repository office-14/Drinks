using System;
using Project.API.Domain.Core;

namespace Project.API.Domain.Drinks
{
    public sealed class Drink
    {
        private Drink(
            int id,
            Name name,
            Description description,
            Uri photoUrl
        )
        {
            Id = id;
            Name = name;
            Description = description;
            PhotoUrl = photoUrl;
        }

        public int Id { get; }

        public Name Name { get; }

        public Description Description { get; }

        public Uri PhotoUrl { get; }

        public static Drink Available(
            int id,
            Name name,
            Description description,
            Uri photoUrl
        ) => new Drink(id, name, description, photoUrl);
    }
}