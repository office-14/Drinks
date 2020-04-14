using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.DependencyInjection;
using Project.API.Ordering.Domain.Drinks;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.AddIns
{
    public class AvailableAddInsTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public AvailableAddInsTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task AddIns_are_available_for_anonymous_user()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("api/add-ins");

            response.EnsureSuccessResponse();
        }

        [Fact]
        public async Task When_unhandled_exception_happens_Then_return_error_object()
        {
            var client = factory
                .WithWebHostBuilder(builder =>
                {
                    builder.ConfigureTestServices(services =>
                    {
                        services.AddSingleton<IAddInsRepository, FailedAddInsRepository>();
                    });
                })
                .CreateAnonymous();

            var response = await client.GetAsync("api/add-ins");

            await response.EnsureServerError();
        }

        private class FailedAddInsRepository : IAddInsRepository
        {
            public Task<AddIn> AddInWithId(AddInId id, CancellationToken token = default)
            {
                throw new System.NotImplementedException();
            }

            public Task<IEnumerable<AddIn>> ListAvailableAddIns(CancellationToken token = default)
            {
                throw new System.NotImplementedException("Not implemented");
            }
        }
    }
}