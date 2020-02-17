using System;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Domain.AddIns;

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

        public static AddInItem From(AddIn addInn) =>
            new AddInItem
            {
                Id = addInn.Id,
                Name = addInn.Name.Value,
                Description = addInn.Description.Value,
                PhotoUrl = addInn.PhotoUrl.ToString(),
                Price = addInn.Price.Amount
            };
    }
}