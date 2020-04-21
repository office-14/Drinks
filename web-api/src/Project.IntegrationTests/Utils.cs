using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Mime;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.WebApi.Endpoints.Ordering.CreateOrder;
using Project.API.WebApi.Endpoints.Shared;
using Xunit;

namespace Project.IntegrationTests
{
    internal static class Utils
    {
        public static async Task<T> Parse<T>(this HttpResponseMessage response)
        {
            var responseString = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<T>(responseString);
        }

        public static async Task<T> ParseApiResponse<T>(this HttpResponseMessage response)
        {
            var parseResult = await response.Parse<ResponseWrapper<T>>();
            return parseResult.Payload;
        }

        public static Task<HttpResponseMessage> PostContentAsync<T>(this HttpClient client, string requestUri, T content)
        {
            var contentAsString = JsonSerializer.Serialize(content);
            var postContent = new StringContent(contentAsString, Encoding.UTF8, MediaTypeNames.Application.Json);
            return client.PostAsync(requestUri, postContent);
        }

        public static async Task EnsureServerError(this HttpResponseMessage response)
        {
            Assert.Equal(HttpStatusCode.InternalServerError, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status500InternalServerError, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }

        public static async Task EnsureBadRequest(this HttpResponseMessage response)
        {
            Assert.Equal(HttpStatusCode.BadRequest, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status400BadRequest, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }

        public static async Task EnsureNotFound(this HttpResponseMessage response)
        {
            Assert.Equal(HttpStatusCode.NotFound, response.StatusCode);
            Assert.Equal("application/json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status404NotFound, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }

        public static void EnsureUnauthorized(this HttpResponseMessage response)
        {
            Assert.Equal(HttpStatusCode.Forbidden, response.StatusCode);
        }

        public static void EnsureUnauthenticated(this HttpResponseMessage response)
        {
            Assert.Equal(HttpStatusCode.Unauthorized, response.StatusCode);
        }

        public static void EnsureSuccess(this HttpResponseMessage response)
        {
            response.EnsureSuccessStatusCode();
            Assert.Equal("application/json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
        }

        public static async Task<HttpResponseMessage> MakeSimpleOrder(this HttpClient client)
        {
            return await client.PostContentAsync(
                "api/orders",
                new CreateOrderDetails
                {
                    Drinks = new List<CreateOrderDrinksItem> {
                        new CreateOrderDrinksItem {
                            DrinkId = 1,
                            SizeId = 7,
                            AddIns = new List<int> { 4 },
                            Count = 1
                        }
                    }
                });
        }

        public static async Task<CreatedOrder> MakeSimpleOrderAndGetDetails(this HttpClient client)
        {
            var response = await client.MakeSimpleOrder();
            return await response.ParseApiResponse<CreatedOrder>();
        }
    }
}