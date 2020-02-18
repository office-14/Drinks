using System;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Application.OrderDetails;

namespace Project.API.WebApi.Endpoints.OrderWithId
{
    public class OrderWithId
    {
        [JsonPropertyName("id")]
        [Required]
        public int Id { get; set; }

        [JsonPropertyName("status_code")]
        [Required]
        public string StatusCode { get; set; }

        [JsonPropertyName("status_name")]
        [Required]
        public string StatusName { get; set; }

        [JsonPropertyName("created_date")]
        [Required]
        public string CreatedDate { get; set; }

        [JsonPropertyName("finish_date")]
        public string FinishDate { get; set; }

        [JsonPropertyName("order_number")]
        [Required]
        public string OrderNumber { get; set; }

        [JsonPropertyName("total_price")]
        [Required]
        public int TotalPrice { get; set; }

        public static OrderWithId From(OrderDetails order)
        {
            return new OrderWithId
            {
                Id = order.Id,
                StatusCode = order.Status.Code,
                StatusName = order.Status.Name,
                TotalPrice = order.TotalPrice.Amount,
                OrderNumber = order.OrderNumber.Value,
                CreatedDate = order.CreatedDate.ToString("o"),
                FinishDate = order.FinishDate?.ToString("o")
            };
        }
    }
}