using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Drinks;

namespace Project.API.Servicing.Application.BookedOrders
{
    public readonly struct BookedItem
    {
        private BookedItem(
            Name drinkName,
            Volume drinkVolume,
            Name[] addIns,
            Quantity count
        )
        {
            DrinkName = drinkName;
            DrinkVolume = drinkVolume;
            AddIns = addIns;
            Count = count;
        }

        public Name DrinkName { get; }

        public Volume DrinkVolume { get; }

        public Name[] AddIns { get; }

        public Quantity Count { get; }

        public static BookedItem Ordered(
            Name drinkName,
            Volume drinkVolume,
            Name[] addIns,
            Quantity count
        ) => new BookedItem(
            drinkName,
            drinkVolume,
            addIns,
            count
        );
    }
}