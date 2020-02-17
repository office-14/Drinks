using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

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
    }
}