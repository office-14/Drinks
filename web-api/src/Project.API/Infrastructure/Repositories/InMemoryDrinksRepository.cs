using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Project.API.Ordering.Application.DrinkDetails;
using Project.API.Ordering.Domain.Core;
using Project.API.Ordering.Domain.Drinks;
using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Drinks;

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
                Name.From("Американо"),
                Description.From("Порция эспрессо, залитая горячей водой, создаёт легкий слой пенки, который завершается в богатой нюансами глубине чашки этого напитка. Совет для профессионалов: чтобы добавить насыщенности вкусу, попросите своего бариста добавить в напиток ещё одну порцию эспрессо."),
                new Uri("https://storage.googleapis.com/images.office-14.com/drinks/americano.jpg")
            ),
            Drink.Available(
                DrinkId.From(2),
                Name.From("Капучино"),
                Description.From("Слой тёмного, насыщенного эспрессо лежит под сглаженным и растянутым слоем густой молочной пены. Алхимия баристского мастерства и ремесла."),
                new Uri("https://storage.googleapis.com/images.office-14.com/drinks/cappuccino.jpg")
            ),
            Drink.Available(
                DrinkId.From(107),
                Name.From("Эспрессо"),
                Description.From("Эспрессо с богатым насыщенным вкусом и карамельными сладкими нотками – самый яркий представитель напитков на основе эспрессо."),
                new Uri("https://storage.googleapis.com/images.office-14.com/drinks/espresso.jpg")
            ),
            Drink.Available(
                DrinkId.From(109),
                Name.From("Латте"),
                Description.From("Тёмный, насыщенный эспрессо сбалансирован пропаренным молоком и легким слоем пены. Идеальный разогрев перед молоком."),
                new Uri("https://storage.googleapis.com/images.office-14.com/drinks/latte.jpg")
            ),
            Drink.Available(
                DrinkId.From(15),
                Name.From("Флэт Уайт"),
                Description.From("Плавные порции эспрессо из ристретто в сочетании с идеальным количеством пропаренного цельного молока создают не слишком сильный, не слишком сливочный, просто правильный вкус."),
                new Uri("https://storage.googleapis.com/images.office-14.com/drinks/flat-white.jpg")
            ),
        };

        private static readonly IEnumerable<DrinkSize> AvailableSizes = new List<DrinkSize> {
            DrinkSize.Available(
                DrinkSizeId.From(7),
                Name.From("Средний"),
                Volume.From("200 мл"),
                Roubles.From(140)
            ),
            DrinkSize.Available(
                DrinkSizeId.From(10),
                Name.From("Большой"),
                Volume.From("300 мл"),
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