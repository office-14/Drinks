using System.Net;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Testing;
using Project.API.WebApi;
using Project.IntegrationTests.TestUser;
using Xunit;

namespace Project.IntegrationTests.Orders
{
    public class OrderWithIdTests
        : IClassFixture<WebApplicationFactory<Startup>>
    {
        private readonly WebApplicationFactory<Startup> factory;

        public OrderWithIdTests(WebApplicationFactory<Startup> factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task When_there_is_no_order_with_provided_id_Then_return_404_with_error_response()
        {
            var client = factory.CreateClientWithTestAuth();

            var response = await client.GetAsync("api/orders/123");

            Assert.Equal(HttpStatusCode.NotFound, response.StatusCode);
            Assert.Equal("application/json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
            var parsedResponse = await response.Parse<ProblemDetails>();
            Assert.Equal(StatusCodes.Status404NotFound, parsedResponse.Status);
            Assert.NotEmpty(parsedResponse.Title);
        }
    }
}