using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Ordering.Application.OrderDetails;

namespace Project.API.WebApi.Endpoints.Ordering.CreateOrder
{
    public class CreatedOrder
    {
        [JsonPropertyName("id")]
        [Required]
        public int Id { get; set; }

        [JsonPropertyName("status_code")]
        [Required]
        public string StatusCode { get; set; } = default!;

        [JsonPropertyName("status_name")]
        [Required]
        public string StatusName { get; set; } = default!;

        [JsonPropertyName("order_number")]
        [Required]
        public string OrderNumber { get; set; } = default!;

        [JsonPropertyName("total_price")]
        [Required]
        public int TotalPrice { get; set; }

        public static CreatedOrder From(OrderDetails order)
        {
            return new CreatedOrder
            {
                Id = order.Id.Value,
                StatusCode = order.Status.Code,
                StatusName = order.Status.Name,
                TotalPrice = order.TotalPrice.Amount,
                OrderNumber = order.OrderNumber.Value
            };
        }
    }
}