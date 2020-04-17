using System.Collections.Generic;
using System.Threading.Tasks;
using Project.API.WebApi.Endpoints.Ordering.CreateOrder;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class CreateOrderTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public CreateOrderTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Anonymous_user_cannot_create_orders()
        {
            var client = factory.CreateAnonymous();

            var response = await client.MakeSimpleOrder();

            response.EnsureUnauthenticated();
        }

        [Fact]
        public async Task Admin_user_cannot_create_orders()
        {
            var client = factory.CreateAdminUser();

            var response = await client.MakeSimpleOrder();

            response.EnsureUnauthorized();
        }

        [Fact]
        public async Task Client_user_can_create_orders()
        {
            var client = factory.CreateClientUser();

            var response = await client.MakeSimpleOrder();

            response.EnsureSuccess();
        }

        [Fact]
        public async Task Client_user_cannot_create_new_order_when_he_has_unfinished_order()
        {
            var client = factory.CreateClientUser();
            await client.MakeSimpleOrder();

            var response = await client.MakeSimpleOrder();

            await response.EnsureBadRequest();
        }

        [Fact]
        public async Task When_create_with_non_existing_drink_Then_return_bad_request()
        {
            var client = factory.CreateClientUser();

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

            await response.EnsureBadRequest();
        }

        [Fact]
        public async Task When_create_with_non_existing_size_Then_return_bad_request()
        {
            var client = factory.CreateClientUser();

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

            await response.EnsureBadRequest();
        }

        [Fact]
        public async Task When_create_with_non_existing_addin_Then_return_bad_request()
        {
            var client = factory.CreateClientUser();

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

            await response.EnsureBadRequest();
        }
    }
}