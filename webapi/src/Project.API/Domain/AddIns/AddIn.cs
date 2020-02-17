using System;
using Project.API.Domain.Core;

namespace Project.API.Domain.AddIns
{
    public sealed class AddIn
    {
        private AddIn(
            int id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles price
        )
        {
            Id = id;
            Name = name;
            Description = description;
            PhotoUrl = photoUrl;
            Price = price;
        }

        public int Id { get; }

        public Name Name { get; }

        public Description Description { get; }

        public Uri PhotoUrl { get; }

        public Roubles Price { get; }

        public static AddIn Available(
            int id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles price
        ) => new AddIn(id, name, description, photoUrl, price);
    }
}