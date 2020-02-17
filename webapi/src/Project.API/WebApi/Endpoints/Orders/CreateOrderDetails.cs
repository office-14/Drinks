using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.Orders
{
    public class CreateOrderDetails
    {
        [JsonPropertyName("drinks")]
        [Required]
        public List<CreateOrderDrinksItem> Drinks { get; set; }
    }

    public class CreateOrderDrinksItem
    {
        [JsonPropertyName("drink_id")]
        [Required]
        public int DrinkId { get; set; }

        [JsonPropertyName("size_id")]
        [Required]
        public int SizeId { get; set; }

        [JsonPropertyName("add-ins")]
        public List<int> AddIns { get; set; }
    }
}