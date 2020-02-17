using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.Drinks
{
    public class DrinkSizeItem
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
    }
}