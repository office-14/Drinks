using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Project.API.Ordering.Application.LastUserOrderStatus;

namespace Project.API.WebApi.Endpoints.Ordering.LastUserOrderStatus
{
    public class LastOrderStatus
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

        public static LastOrderStatus From(LastOrderStatusDetails statusDetails)
        {
            return new LastOrderStatus
            {
                Id = statusDetails.OrderId.Value,
                StatusCode = statusDetails.Status.Code,
                StatusName = statusDetails.Status.Name
            };
        }
    }
}