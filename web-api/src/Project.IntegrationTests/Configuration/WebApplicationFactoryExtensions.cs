using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Json;
using Microsoft.AspNetCore.Mvc.Testing;

namespace Project.IntegrationTests.Configuration
{
    internal static class WebApplicationFactoryExtensions
    {
        internal static HttpClient CreateAnonymous<T>(this WebApplicationFactory<T> factory) where T : class
            => factory.DefaultApiClient();

        internal static HttpClient CreateClientUser<T>(this WebApplicationFactory<T> factory) where T : class
        {
            var client = factory.DefaultApiClient();

            var jsonCredentials = JsonSerializer.Serialize(new TestAuthHandler.Credentials
            {
                Id = Guid.NewGuid().ToString(),
                IsAdmin = false
            });

            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue(TestAuthHandler.TestScheme, jsonCredentials);

            return client;
        }

        internal static HttpClient CreateAdminUser<T>(this WebApplicationFactory<T> factory) where T : class
        {
            var client = factory.DefaultApiClient();

            var jsonCredentials = JsonSerializer.Serialize(new TestAuthHandler.Credentials
            {
                Id = Guid.NewGuid().ToString(),
                IsAdmin = true
            });

            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue(TestAuthHandler.TestScheme, jsonCredentials);

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