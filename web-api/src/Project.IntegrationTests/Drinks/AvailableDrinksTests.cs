using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.DependencyInjection;
using Project.API.Ordering.Application.DrinkDetails;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Drinks
{
    public class AvailableDrinksTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public AvailableDrinksTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Drinks_are_available_for_anonymous_user()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("api/drinks");

            response.EnsureSuccess();
        }

        [Fact]
        public async Task When_unhandled_exception_happens_Then_return_error_object()
        {
            var client = factory
                .WithWebHostBuilder(builder =>
                {
                    builder.ConfigureTestServices(services =>
                    {
                        services.AddSingleton<IDrinkDetailsRepository, FailedDrinksRepository>();
                    });
                })
                .CreateAnonymous();

            var response = await client.GetAsync("api/drinks");

            await response.EnsureServerError();
        }

        private class FailedDrinksRepository : IDrinkDetailsRepository
        {
            public Task<IEnumerable<DrinkDetails>> AvailableDrinkDetails(CancellationToken token = default)
            {
                throw new System.NotImplementedException("Not implemented");
            }
        }
    }
}