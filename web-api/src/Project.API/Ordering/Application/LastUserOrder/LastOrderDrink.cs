using System.Collections.Generic;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.Ordering.Application.LastUserOrder
{
    public readonly struct LastOrderDrink
    {
        private LastOrderDrink(
            DrinkDetails drink,
            DrinkSizeDetails drinkSize,
            List<AddInDetails> addIns,
            Quantity count,
            Roubles price
        )
        {
            Drink = drink;
            DrinkSize = drinkSize;
            AddIns = addIns;
            Count = count;
            Price = price;
        }

        public DrinkDetails Drink { get; }

        public DrinkSizeDetails DrinkSize { get; }

        public List<AddInDetails> AddIns { get; }

        public Quantity Count { get; }

        public Roubles Price { get; }

        public static LastOrderDrink Available(
            DrinkDetails drink,
            DrinkSizeDetails drinkSize,
            List<AddInDetails> addIns,
            Quantity count,
            Roubles price
        ) => new LastOrderDrink(
            drink,
            drinkSize,
            addIns,
            count,
            price
        );
    }
}