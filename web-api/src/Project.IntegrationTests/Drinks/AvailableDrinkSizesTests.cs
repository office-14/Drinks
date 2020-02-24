using System.Net;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Testing;
using Project.API.WebApi;
using Xunit;

namespace Project.IntegrationTests.Drinks
{
    public class AvailableDrinkSizesTests
        : IClassFixture<WebApplicationFactory<Startup>>
    {
        private readonly WebApplicationFactory<Startup> factory;

        public AvailableDrinkSizesTests(WebApplicationFactory<Startup> factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task When_there_is_no_drink_with_provided_id_Then_return_404_with_error_response()
        {
            var client = factory.CreateClient();

            var response = await client.GetAsync("api/drinks/777/sizes");

            Assert.Equal(HttpStatusCode.NotFound, response.StatusCode);
            Assert.Equal("application/json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status404NotFound, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }
    }
}