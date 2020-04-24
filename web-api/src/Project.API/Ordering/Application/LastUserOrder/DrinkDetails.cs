using System;
using Project.API.Ordering.Domain.Drinks;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.Ordering.Application.LastUserOrder
{
    public sealed class DrinkDetails
    {
        private DrinkDetails(DrinkId id, Name name, Uri photoUrl) =>
            (Id, Name, PhotoUrl) = (id, name, photoUrl);

        public DrinkId Id { get; }

        public Name Name { get; }

        public Uri PhotoUrl { get; }

        public static DrinkDetails Ordered(
            DrinkId id,
            Name name,
            Uri photoUrl
        ) => new DrinkDetails(
            id,
            name,
            photoUrl
        );
    }
}