using System.Threading.Tasks;
using Project.API.WebApi.Endpoints.Ordering.LastUserOrderStatus;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class LastUserOrderStatusTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public LastUserOrderStatusTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Anonymous_user_cannot_get_last_order_status()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("/api/user/orders/last/status");

            response.EnsureUnauthenticated();
        }

        [Fact]
        public async Task Admin_user_cannot_get_last_order_status()
        {
            var client = factory.CreateAdminUser();

            var response = await client.GetAsync("/api/user/orders/last/status");

            response.EnsureUnauthorized();
        }

        [Fact]
        public async Task When_client_user_doesnt_have_last_order_Then_return_null_in_payload()
        {
            var client = factory.CreateClientUser();

            var response = await client.GetAsync("/api/user/orders/last/status");

            response.EnsureSuccess();
            var order = await response.ParseApiResponse<LastOrderStatus>();
            Assert.Null(order);
        }

        [Fact]
        public async Task When_user_creates_new_order_Then_it_becomes_last_order_status()
        {
            var client = factory.CreateClientUser();
            var order = await client.MakeSimpleOrderAndGetDetails();

            var response = await client.GetAsync("api/user/orders/last/status");

            response.EnsureSuccess();
            var lastOrder = await response.ParseApiResponse<LastOrderStatus>();
            Assert.NotNull(order);
            Assert.Equal(order.Id, lastOrder.Id);
        }

        [Fact]
        public async Task When_order_becomes_ready_Then_last_order_status_also_ready()
        {
            var client = factory.CreateClientUser();
            var order = await client.MakeSimpleOrderAndGetDetails();

            var admin = factory.CreateAdminUser();
            await admin.PostAsync($"api/orders/{order.Id}/finish", null);

            var response = await client.GetAsync("api/user/orders/last/status");

            response.EnsureSuccess();
            var lastOrder = await response.ParseApiResponse<LastOrderStatus>();
            Assert.Equal("READY", lastOrder.StatusCode);
        }
    }
}