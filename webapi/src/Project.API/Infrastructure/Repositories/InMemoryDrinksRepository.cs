using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Project.API.Application.DrinkDetails;
using Project.API.Domain.Core;
using Project.API.Domain.Drinks;

namespace Project.API.Infrastructure.Repositories
{
    public sealed class InMemoryDrinksRepository :
        IDrinksRepository,
        IDrinkSizesRepository,
        IDrinkDetailsRepository
    {
        private static readonly IEnumerable<Drink> AvailableDrinks = new List<Drink>{
            Drink.Available(
                DrinkId.From(1),
                Name.From("Caffè Americano"),
                Description.From("Espresso shots topped with hot water create a light layer of crema culminating in this wonderfully rich cup with depth and nuance. Pro Tip: For an additional boost, ask your barista to try this with an extra shot."),
                new Uri("https://globalassets.starbucks.com/assets/f12bc8af498d45ed92c5d6f1dac64062.jpg?impolicy=1by1_wide_1242")
            ),
            Drink.Available(
                DrinkId.From(2),
                Name.From("Cappuccino"),
                Description.From("Dark, rich espresso lies in wait under a smoothed and stretched layer of thick milk foam. An alchemy of barista artistry and craft."),
                new Uri("https://globalassets.starbucks.com/assets/5c515339667943ce84dc56effdf5fc1b.jpg?impolicy=1by1_wide_1242")
            ),
            Drink.Available(
                DrinkId.From(107),
                Name.From("Espresso"),
                Description.From("Our smooth signature Espresso Roast with rich flavor and caramelly sweetness is at the very heart of everything we do."),
                new Uri("https://globalassets.starbucks.com/assets/ec519dd5642c41629194192cce582135.jpg?impolicy=1by1_wide_1242")
            ),
            Drink.Available(
                DrinkId.From(109),
                Name.From("Caffè Latte"),
                Description.From("Our dark, rich espresso balanced with steamed milk and a light layer of foam. A perfect milk-forward warm-up."),
                new Uri("https://globalassets.starbucks.com/assets/b635f407bbcd49e7b8dd9119ce33f76e.jpg?impolicy=1by1_wide_1242")
            ),
            Drink.Available(
                DrinkId.From(15),
                Name.From("Flat White"),
                Description.From("Smooth ristretto shots of espresso get the perfect amount of steamed whole milk to create a not-too-strong, not-too-creamy, just-right flavor."),
                new Uri("https://globalassets.starbucks.com/assets/fedee22e49724cd09fbcc7ee2e567377.jpg?impolicy=1by1_wide_1242")
            ),
        };

        private static readonly IEnumerable<DrinkSize> AvailableSizes = new List<DrinkSize> {
            DrinkSize.Available(
                DrinkSizeId.From(7),
                Name.From("Medium"),
                Volume.From("200 ml"),
                Roubles.From(140)
            ),
            DrinkSize.Available(
                DrinkSizeId.From(10),
                Name.From("Large"),
                Volume.From("300 ml"),
                Roubles.From(250)
            )
        };

        public Task<IEnumerable<DrinkDetails>> AvailableDrinkDetails(CancellationToken token = default)
        {
            return Task.FromResult(AvailableDrinks.
                Select(ad => DrinkDetails.Available(
                    ad.Id,
                    ad.Name,
                    ad.Description,
                    ad.PhotoUrl,
                    Roubles.From(140)
                )));
        }

        public Task<Drink?> DrinkWithId(DrinkId id, CancellationToken token = default)
        {
            return Task.FromResult<Drink?>(AvailableDrinks.FirstOrDefault(d => d.Id.Equals(id)));
        }

        public Task<IEnumerable<Drink>> ListAvailableDrinks(CancellationToken token = default)
        {
            return Task.FromResult(AvailableDrinks);
        }

        public Task<IEnumerable<DrinkSize>?> ListSizesOfDrink(DrinkId drinkId, CancellationToken token = default)
        {
            if (!AvailableDrinks.Select(d => d.Id).Contains(drinkId))
            {
                return Task.FromResult<IEnumerable<DrinkSize>?>(null);
            }

            return Task.FromResult<IEnumerable<DrinkSize>?>(AvailableSizes);
        }

        public async Task<DrinkSize?> SizeOfDrink(DrinkId drinkId, DrinkSizeId drinkSizeId, CancellationToken token = default)
        {
            var availableSizes = await ListSizesOfDrink(drinkId, token);

            return availableSizes.FirstOrDefault(s => s.Id.Equals(drinkSizeId));
        }
    }
}