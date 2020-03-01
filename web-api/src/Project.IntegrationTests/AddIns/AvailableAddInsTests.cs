using System.Collections.Generic;
using System.Net;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.DependencyInjection;
using Project.API.Ordering.Domain.Drinks;
using Project.API.WebApi;
using Xunit;

namespace Project.IntegrationTests.AddIns
{
    public class AvailableAddInsTests
        : IClassFixture<WebApplicationFactory<Startup>>
    {
        private static readonly string AvailableAddInsUrl = "api/add-ins";

        private readonly WebApplicationFactory<Startup> factory;

        public AvailableAddInsTests(WebApplicationFactory<Startup> factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task When_there_are_add_ins_Then_return_them_all()
        {
            var client = factory.CreateClient();

            var response = await client.GetAsync(AvailableAddInsUrl);

            response.EnsureSuccessStatusCode();
            Assert.Equal("application/json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
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
                .CreateClient();

            var response = await client.GetAsync(AvailableAddInsUrl);

            Assert.Equal(HttpStatusCode.InternalServerError, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status500InternalServerError, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
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