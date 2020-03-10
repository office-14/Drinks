using System.Collections.Generic;
using System.Net;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Testing;
using Project.API.WebApi;
using Project.API.WebApi.Endpoints.Ordering.CreateOrder;
using Project.API.WebApi.Endpoints.Ordering.Shared;
using Project.API.WebApi.Endpoints.Shared;
using Project.IntegrationTests.TestUser;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class FinishOrderTests
        : IClassFixture<WebApplicationFactory<Startup>>
    {
        private readonly WebApplicationFactory<Startup> factory;

        public FinishOrderTests(WebApplicationFactory<Startup> factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task When_there_is_no_order_with_provided_id_Then_return_404_with_error_response()
        {
            var client = factory.CreateClientWithTestAuth();

            var response = await client.PostAsync("api/orders/123/finish", null);

            Assert.Equal(HttpStatusCode.NotFound, response.StatusCode);
            Assert.Equal("application/json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status404NotFound, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }

        [Fact]
        public async Task When_finish_order_twice_Then_return_bad_request()
        {
            var client = factory.CreateClientWithTestAuth();
            var createdOrderResponse = await client.PostContentAsync(
                "api/orders",
                new CreateOrderDetails
                {
                    Drinks = new List<CreateOrderDrinksItem> {
                        new CreateOrderDrinksItem {
                            DrinkId = 1,
                            SizeId = 7
                        }
                    }
                });
            var orderResponse = await createdOrderResponse.Parse<ResponseWrapper<SingleOrder>>();
            var createdOrderId = orderResponse.Payload.Id;
            await client.PostAsync($"api/orders/{createdOrderId}/finish", null);

            var response = await client.PostAsync($"api/orders/{createdOrderId}/finish", null);

            Assert.Equal(HttpStatusCode.BadRequest, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status400BadRequest, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }
    }
}