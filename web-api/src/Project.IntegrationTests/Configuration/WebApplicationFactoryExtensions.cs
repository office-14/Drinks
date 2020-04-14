using System.Net.Http;
using System.Net.Http.Headers;
using Microsoft.AspNetCore.Mvc.Testing;

namespace Project.IntegrationTests.Configuration
{
    internal static class WebApplicationFactoryExtensions
    {
        internal static HttpClient CreateAnonymous<T>(this WebApplicationFactory<T> factory) where T : class
            => factory.DefaultApiClient();

        internal static HttpClient CreateAlice<T>(this WebApplicationFactory<T> factory) where T : class
        {
            var client = factory.DefaultApiClient();

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(TestAuthHandler.TestScheme, "Alice");

            return client;
        }

        internal static HttpClient CreateBob<T>(this WebApplicationFactory<T> factory) where T : class
        {
            var client = factory.DefaultApiClient();

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(TestAuthHandler.TestScheme, "Bob");

            return client;
        }

        internal static HttpClient CreateAdminZod<T>(this WebApplicationFactory<T> factory) where T : class
        {
            var client = factory.DefaultApiClient();

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(TestAuthHandler.TestScheme, "Zod");

            return client;
        }

        private static HttpClient DefaultApiClient<T>(this WebApplicationFactory<T> factory) where T : class
        {
            return factory.CreateClient(new WebApplicationFactoryClientOptions
            {
                AllowAutoRedirect = false
            });
        }
    }
}