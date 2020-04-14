using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.Infrastructure.DeviceTokens
{
    public class UpdateDeviceToken
    {
        [JsonPropertyName("device_id")]
        [Required]
        public string DeviceId { get; set; } = default!;

        [JsonPropertyName("token")]
        [Required]
        public string Token { get; set; } = default!;
    }
}