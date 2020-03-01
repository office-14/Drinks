using System;
using Project.API.Ordering.Domain.Core;
using Project.API.Ordering.Domain.Drinks;

namespace Project.API.Ordering.Application.DrinkDetails
{
    public sealed class DrinkDetails
    {
        private DrinkDetails(
            DrinkId id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles priceOfSmallestSize
        ) =>
            (Id, Name, Description, PhotoUrl, PriceOfSmallestSize) =
            (id, name, description, photoUrl, priceOfSmallestSize);

        public DrinkId Id { get; }

        public Name Name { get; }

        public Description Description { get; }

        public Uri PhotoUrl { get; }

        public Roubles PriceOfSmallestSize { get; }

        public static DrinkDetails Available(
            DrinkId id,
            Name name,
            Description description,
            Uri photoUrl,
            Roubles priceOfSmallestSize
        ) => new DrinkDetails(id, name, description, photoUrl, priceOfSmallestSize);
    }
}