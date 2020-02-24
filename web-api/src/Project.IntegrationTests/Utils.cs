using System.Net.Http;
using System.Net.Mime;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace Project.IntegrationTests
{
    internal static class Utils
    {
        public static async Task<T> Parse<T>(this HttpResponseMessage response)
        {
            var responseString = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<T>(responseString);
        }

        public static Task<HttpResponseMessage> PostContentAsync<T>(this HttpClient client, string requestUri, T content)
        {
            var contentAsString = JsonSerializer.Serialize(content);
            var postContent = new StringContent(contentAsString, Encoding.UTF8, MediaTypeNames.Application.Json);
            return client.PostAsync(requestUri, postContent);
        }
    }
}