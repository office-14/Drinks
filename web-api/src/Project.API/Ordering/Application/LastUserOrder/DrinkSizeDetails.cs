using Project.API.Ordering.Domain.Drinks;
using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Drinks;

namespace Project.API.Ordering.Application.LastUserOrder
{
    public sealed class DrinkSizeDetails
    {
        private DrinkSizeDetails(DrinkSizeId sizeId, Volume volume, Name name) =>
            (SizeId, Volume, Name) = (sizeId, volume, name);

        public DrinkSizeId SizeId { get; }

        public Volume Volume { get; }

        public Name Name { get; }

        public static DrinkSizeDetails Ordered(
            DrinkSizeId sizeId,
            Volume volume,
            Name name
        ) => new DrinkSizeDetails(
            sizeId,
            volume,
            name
        );
    }
}