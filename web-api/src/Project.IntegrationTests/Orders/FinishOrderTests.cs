using System.Collections.Generic;
using System.Threading.Tasks;
using Project.API.WebApi.Endpoints.Ordering.CreateOrder;
using Project.API.WebApi.Endpoints.Ordering.Shared;
using Project.API.WebApi.Endpoints.Shared;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class FinishOrderTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public FinishOrderTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Anonymous_user_cannot_finish_orders()
        {
            var client = factory.CreateAnonymous();

            var response = await client.PostAsync("api/orders/123/finish", null);

            response.EnsureUnauthenticated();
        }

        [Fact]
        public async Task When_there_is_no_order_with_provided_id_Then_return_404_with_error_response()
        {
            var client = factory.CreateAlice();

            var response = await client.PostAsync("api/orders/123/finish", null);

            await response.EnsureNotFound();
        }

        [Fact]
        public async Task When_finish_order_twice_Then_return_bad_request()
        {
            var client = factory.CreateAlice();
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

            await response.EnsureBadRequest();
        }
    }
}