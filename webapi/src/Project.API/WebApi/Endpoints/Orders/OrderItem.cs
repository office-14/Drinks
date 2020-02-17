using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.Orders
{
    public class OrderItem
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
    }
}