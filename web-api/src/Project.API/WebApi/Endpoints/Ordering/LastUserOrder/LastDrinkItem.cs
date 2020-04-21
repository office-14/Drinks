using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using Project.API.Ordering.Application.LastUserOrder;

namespace Project.API.WebApi.Endpoints.Ordering.LastUserOrder
{
    public class LastDrinkItem
    {
        [JsonPropertyName("drink_id")]
        [Required]
        public int DrinkId { get; set; }

        [JsonPropertyName("size_id")]
        [Required]
        public int DrinkSizeId { get; set; }

        [JsonPropertyName("add-ins")]
        [Required]
        public IEnumerable<int> AddIns { get; set; } = Enumerable.Empty<int>();

        [JsonPropertyName("count")]
        [Required]
        public int Count { get; set; }

        public static LastDrinkItem From(LastOrderDrink drink)
        {
            return new LastDrinkItem
            {
                DrinkId = drink.DrinkId.Value,
                DrinkSizeId = drink.DrinkSizeId.Value,
                AddIns = drink.AddIns.Select(a => a.Value),
                Count = drink.Count.Value
            };
        }
    }
}