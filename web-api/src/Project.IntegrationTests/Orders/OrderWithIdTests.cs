using System.Threading.Tasks;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class OrderWithIdTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public OrderWithIdTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Anonymous_user_cannot_get_order_by_id()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("api/orders/123");

            response.EnsureUnauthenticated();
        }

        [Fact]
        public async Task When_there_is_no_order_with_provided_id_Then_return_404_with_error_response()
        {
            var client = factory.CreateClientUser();

            var response = await client.GetAsync("api/orders/123");

            await response.EnsureNotFound();
        }
    }
}