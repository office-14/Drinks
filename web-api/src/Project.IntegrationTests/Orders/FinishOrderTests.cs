using System.Threading.Tasks;
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
        public async Task Client_user_cannot_finish_orders()
        {
            var client = factory.CreateClientUser();

            var response = await client.PostAsync("api/orders/123/finish", null);

            response.EnsureUnauthorized();
        }

        [Fact]
        public async Task Admin_user_can_finish_orders()
        {
            var client = factory.CreateClientUser();
            var order = await client.MakeSimpleOrderAndGetDetails();

            var admin = factory.CreateAdminUser();
            var response = await admin.PostAsync($"api/orders/{order.Id}/finish", null);

            response.EnsureSuccessStatusCode();
        }

        [Fact]
        public async Task When_there_is_no_order_with_provided_id_Then_return_404_with_error_response()
        {
            var client = factory.CreateAdminUser();

            var response = await client.PostAsync("api/orders/123/finish", null);

            await response.EnsureNotFound();
        }

        [Fact]
        public async Task When_finish_order_twice_Then_return_bad_request()
        {
            var client = factory.CreateClientUser();
            var order = await client.MakeSimpleOrderAndGetDetails();

            var admin = factory.CreateAdminUser();
            await admin.PostAsync($"api/orders/{order.Id}/finish", null);

            var response = await admin.PostAsync($"api/orders/{order.Id}/finish", null);

            await response.EnsureBadRequest();
        }
    }
}