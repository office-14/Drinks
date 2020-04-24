using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using Project.API.Ordering.Application.LastUserOrder;

namespace Project.API.WebApi.Endpoints.Ordering.LastUserOrder
{
    public class LastDrinkItem
    {
        [JsonPropertyName("drink")]
        [Required]
        public DrinkItem Drink { get; set; } = default!;

        [JsonPropertyName("drink_size")]
        [Required]
        public DrinkSizeItem DrinkSize { get; set; } = default!;

        [JsonPropertyName("add-ins")]
        [Required]
        public IEnumerable<AddInItem> AddIns { get; set; } = Enumerable.Empty<AddInItem>();

        [JsonPropertyName("count")]
        [Required]
        public int Count { get; set; }

        [JsonPropertyName("price")]
        [Required]
        public int Price { get; set; }

        public static LastDrinkItem From(LastOrderDrink drink)
        {
            return new LastDrinkItem
            {
                Drink = DrinkItem.From(drink.Drink),
                DrinkSize = DrinkSizeItem.From(drink.DrinkSize),
                AddIns = drink.AddIns.Select(AddInItem.From),
                Count = drink.Count.Value,
                Price = drink.Price.Amount
            };
        }

        public sealed class DrinkItem
        {
            [JsonPropertyName("id")]
            [Required]
            public int Id { get; set; }

            [JsonPropertyName("name")]
            [Required]
            public string Name { get; set; } = default!;

            [JsonPropertyName("photo_url")]
            [Required]
            public string PhotoUrl { get; set; } = default!;

            public static DrinkItem From(DrinkDetails drink)
            {
                return new DrinkItem
                {
                    Id = drink.Id.Value,
                    Name = drink.Name.Value,
                    PhotoUrl = drink.PhotoUrl.ToString()
                };
            }
        }

        public sealed class DrinkSizeItem
        {
            [JsonPropertyName("id")]
            [Required]
            public int Id { get; set; }

            [JsonPropertyName("name")]
            [Required]
            public string Name { get; set; } = default!;

            [JsonPropertyName("volume")]
            [Required]
            public string Volume { get; set; } = default!;

            public static DrinkSizeItem From(DrinkSizeDetails drinkSize)
            {

                return new DrinkSizeItem
                {
                    Id = drinkSize.SizeId.Value,
                    Name = drinkSize.Name.Value,
                    Volume = drinkSize.Volume.Value
                };
            }
        }

        public sealed class AddInItem
        {
            [JsonPropertyName("id")]
            [Required]
            public int Id { get; set; }

            [JsonPropertyName("name")]
            [Required]
            public string Name { get; set; } = default!;

            public static AddInItem From(AddInDetails addIn)
            {
                return new AddInItem
                {
                    Id = addIn.AddInId.Value,
                    Name = addIn.Name.Value
                };
            }
        }
    }
}