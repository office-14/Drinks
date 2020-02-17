using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Domain.Drinks;

namespace Project.API.WebApi.Endpoints.AvailableDrinkSizes
{
    public class AvailableSize
    {
        [JsonPropertyName("id")]
        [Required]
        public int Id { get; set; }

        [JsonPropertyName("volume")]
        [Required]
        public string Volume { get; set; }

        [JsonPropertyName("name")]
        [Required]
        public string Name { get; set; }

        [JsonPropertyName("price")]
        [Required]
        public int Price { get; set; }

        public static AvailableSize From(DrinkSize drinkSize) =>
            new AvailableSize
            {
                Id = drinkSize.Id,
                Volume = drinkSize.Volume.Value,
                Name = drinkSize.Name.Value,
                Price = drinkSize.Price.Amount
            };
    }
}