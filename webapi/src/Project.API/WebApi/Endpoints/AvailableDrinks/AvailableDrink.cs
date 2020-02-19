using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Application.DrinkDetails;

namespace Project.API.WebApi.Endpoints.AvailableDrinks
{
    public class AvailableDrink
    {
        [JsonPropertyName("id")]
        [Required]
        public int Id { get; set; }

        [JsonPropertyName("name")]
        [Required]
        public string Name { get; set; }

        [JsonPropertyName("description")]
        [Required]
        public string Description { get; set; }

        [JsonPropertyName("photo_url")]
        [Required]
        public string PhotoUrl { get; set; }

        [JsonPropertyName("smallest_size_price")]
        [Required]
        public int PriceOfSmallestSize { get; set; }

        public static AvailableDrink From(DrinkDetails drink) =>
            new AvailableDrink
            {
                Id = drink.Id,
                Name = drink.Name.Value,
                Description = drink.Description.Value,
                PhotoUrl = drink.PhotoUrl.ToString(),
                PriceOfSmallestSize = drink.PriceOfSmallestSize.Amount
            };
    }
}