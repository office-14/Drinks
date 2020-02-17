using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.AddIns
{
    public class AddInItem
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

        [JsonPropertyName("price")]
        [Required]
        public int Price { get; set; }
    }
}