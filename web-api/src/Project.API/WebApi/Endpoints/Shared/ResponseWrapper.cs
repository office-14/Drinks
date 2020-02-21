using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.Shared
{
    public class ResponseWrapper<T>
    {
        [JsonPropertyName("payload")]
        public T Payload { get; set; } = default!;

        public static ResponseWrapper<T> From(T payload) =>
            new ResponseWrapper<T>
            {
                Payload = payload
            };
    }
}