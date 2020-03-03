using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Drinks;

namespace Project.API.Servicing.Application.BookedOrders
{
    public readonly struct BookedItem
    {
        private BookedItem(Name drinkName, Volume drinkVolume, Name[] addIns)
        {
            DrinkName = drinkName;
            DrinkVolume = drinkVolume;
            AddIns = addIns;
        }

        public Name DrinkName { get; }

        public Volume DrinkVolume { get; }

        public Name[] AddIns { get; }

        public static BookedItem Ordered(
            Name drinkName,
            Volume drinkVolume,
            Name[] addIns
        ) => new BookedItem(
            drinkName,
            drinkVolume,
            addIns
        );
    }
}