using System.Collections.Generic;
using System.Net;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Testing;
using Project.API.WebApi;
using Project.API.WebApi.Endpoints.CreateOrder;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class CreateOrderTests
        : IClassFixture<WebApplicationFactory<Startup>>
    {
        private readonly WebApplicationFactory<Startup> factory;

        public CreateOrderTests(WebApplicationFactory<Startup> factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task When_create_with_non_existing_drink_Then_return_bad_request()
        {
            var client = factory.CreateClient();

            var response = await client.PostContentAsync(
                "api/orders",
                new CreateOrderDetails
                {
                    Drinks = new List<CreateOrderDrinksItem> {
                        new CreateOrderDrinksItem {
                            DrinkId = 777,
                            SizeId = 1
                        }
                    }
                });

            Assert.Equal(HttpStatusCode.BadRequest, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status400BadRequest, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }

        [Fact]
        public async Task When_create_with_non_existing_size_Then_return_bad_request()
        {
            var client = factory.CreateClient();

            var response = await client.PostContentAsync(
                "api/orders",
                new CreateOrderDetails
                {
                    Drinks = new List<CreateOrderDrinksItem> {
                        new CreateOrderDrinksItem {
                            DrinkId = 1,
                            SizeId = 777
                        }
                    }
                });

            Assert.Equal(HttpStatusCode.BadRequest, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status400BadRequest, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }

        [Fact]
        public async Task When_create_with_non_existing_addin_Then_return_bad_request()
        {
            var client = factory.CreateClient();

            var response = await client.PostContentAsync(
                "api/orders",
                new CreateOrderDetails
                {
                    Drinks = new List<CreateOrderDrinksItem> {
                        new CreateOrderDrinksItem {
                            DrinkId = 1,
                            SizeId = 7,
                            AddIns = new List<int> { 777 }
                        }
                    }
                });

            Assert.Equal(HttpStatusCode.BadRequest, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status400BadRequest, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }
    }
}