using System;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.Servicing.BookedOrders
{
    public class BookedItem
    {
        [JsonPropertyName("drink_name")]
        [Required]
        public string DrinkName { get; set; } = default!;

        [JsonPropertyName("drink_volume")]
        [Required]
        public string DrinkVolume { get; set; } = default!;

        [JsonPropertyName("add-ins")]
        [Required]
        public string[] AddIns { get; set; } = Array.Empty<string>();

        [JsonPropertyName("count")]
        [Required]
        public int Count { get; set; }

        public static BookedItem From(API.Servicing.Application.BookedOrders.BookedItem bookedItem) =>
            new BookedItem
            {
                DrinkName = bookedItem.DrinkName.Value,
                DrinkVolume = bookedItem.DrinkVolume.Value,
                AddIns = bookedItem.AddIns.Select(a => a.Value).ToArray(),
                Count = bookedItem.Count.Value
            };
    }
}