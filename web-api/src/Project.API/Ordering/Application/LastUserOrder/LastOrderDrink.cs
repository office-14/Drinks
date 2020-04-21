using System.Collections.Generic;
using Project.API.Ordering.Domain.Drinks;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.Ordering.Application.LastUserOrder
{
    public readonly struct LastOrderDrink
    {
        private LastOrderDrink(
            DrinkId drinkId,
            DrinkSizeId drinkSizeId,
            List<AddInId> addIns,
            Quantity count
        )
        {
            DrinkId = drinkId;
            DrinkSizeId = drinkSizeId;
            AddIns = addIns;
            Count = count;
        }

        public DrinkId DrinkId { get; }

        public DrinkSizeId DrinkSizeId { get; }

        public List<AddInId> AddIns { get; }

        public Quantity Count { get; }

        public static LastOrderDrink Available(
            DrinkId drinkId,
            DrinkSizeId drinkSizeId,
            List<AddInId> addIns,
            Quantity count
        ) => new LastOrderDrink(
            drinkId,
            drinkSizeId,
            addIns,
            count
        );
    }
}