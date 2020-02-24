using System.Collections.Generic;
using System.Net;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.DependencyInjection;
using Project.API.Application.DrinkDetails;
using Project.API.WebApi;
using Xunit;

namespace Project.IntegrationTests.Drinks
{
    public class AvailableDrinksTests
        : IClassFixture<WebApplicationFactory<Startup>>
    {
        private static readonly string AvailableDrinksUrl = "api/drinks";

        private readonly WebApplicationFactory<Startup> factory;

        public AvailableDrinksTests(WebApplicationFactory<Startup> factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task When_there_are_drinks_Then_return_them_all()
        {
            var client = factory.CreateClient();

            var response = await client.GetAsync(AvailableDrinksUrl);

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
                        services.AddSingleton<IDrinkDetailsRepository, FailedDrinksRepository>();
                    });
                })
                .CreateClient();

            var response = await client.GetAsync(AvailableDrinksUrl);

            Assert.Equal(HttpStatusCode.InternalServerError, response.StatusCode);
            Assert.Equal("application/problem+json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status500InternalServerError, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
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