using System.Threading.Tasks;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Drinks
{
    public class AvailableDrinkSizesTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public AvailableDrinkSizesTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Drink_sizes_are_available_for_anonymous_user()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("api/drinks/1/sizes");

            response.EnsureSuccess();
        }

        [Fact]
        public async Task When_there_is_no_drink_with_provided_id_Then_return_404_with_error_response()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("api/drinks/777/sizes");

            await response.EnsureNotFound();
        }
    }
}