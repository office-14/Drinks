using System.Threading.Tasks;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class BookedOrdersTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public BookedOrdersTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Anonymous_user_cannot_see_booked_orders()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("api/orders/booked");

            response.EnsureUnauthenticated();
        }

        [Fact]
        public async Task Client_user_cannot_see_booked_orders()
        {
            var client = factory.CreateClientUser();

            var response = await client.GetAsync("api/orders/booked");

            response.EnsureUnauthorized();
        }

        [Fact]
        public async Task Admin_user_can_see_booked_orders()
        {
            var admin = factory.CreateAdminUser();

            var response = await admin.GetAsync("api/orders/booked");

            response.EnsureSuccess();
        }
    }
}