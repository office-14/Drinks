using System;
using Project.API.Domain.Core;

namespace Project.API.Application.DrinkDetails
{
    public sealed class DrinkDetails
    {
        private DrinkDetails(
            int id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles priceOfSmallestSize
        )
        {
            Id = id;
            Name = name;
            Description = description;
            PhotoUrl = photoUrl;
            PriceOfSmallestSize = priceOfSmallestSize;
        }

        public int Id { get; }

        public Name Name { get; }

        public Description Description { get; }

        public Uri PhotoUrl { get; }

        public Roubles PriceOfSmallestSize { get; }

        public static DrinkDetails Available(
            int id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles priceOfSmallestSize
        ) => new DrinkDetails(id, name, description, photoUrl, priceOfSmallestSize);
    }
}