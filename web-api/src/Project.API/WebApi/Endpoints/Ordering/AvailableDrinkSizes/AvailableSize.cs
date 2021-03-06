using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Ordering.Domain.Drinks;

namespace Project.API.WebApi.Endpoints.Ordering.AvailableDrinkSizes
{
    public class AvailableSize
    {
        [JsonPropertyName("id")]
        [Required]
        public int Id { get; set; }

        [JsonPropertyName("volume")]
        [Required]
        public string Volume { get; set; } = default!;

        [JsonPropertyName("name")]
        [Required]
        public string Name { get; set; } = default!;

        [JsonPropertyName("price")]
        [Required]
        public int Price { get; set; }

        public static AvailableSize From(DrinkSize drinkSize) =>
            new AvailableSize
            {
                Id = drinkSize.Id.Value,
                Volume = drinkSize.Volume.Value,
                Name = drinkSize.Name.Value,
                Price = drinkSize.Price.Amount
            };
    }
}