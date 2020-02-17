using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Domain.Drinks;

namespace Project.API.WebApi.Endpoints.Drinks
{
    public class DrinkItem
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

        public static DrinkItem From(Drink drink)
        {
            var drinkItem = new DrinkItem();

            drinkItem.Id = drink.Id;
            drinkItem.Name = drink.Name.Value;
            drinkItem.Description = drink.Description.Value;
            drinkItem.PhotoUrl = drink.PhotoUrl.ToString();

            return drinkItem;
        }
    }
}