using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc.Testing;
using Project.API.WebApi;
using Xunit;

namespace Project.IntegrationTests.Infrastructure
{
    public class SwaggerTests
        : IClassFixture<WebApplicationFactory<Startup>>
    {
        private readonly WebApplicationFactory<Startup> factory;

        public SwaggerTests(WebApplicationFactory<Startup> factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Swagger_UI_page_should_be_accessible()
        {
            var client = factory.CreateClient();

            var response = await client.GetAsync("swagger");

            response.EnsureSuccessStatusCode();
            Assert.Equal("text/html; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
        }

        [Theory]
        [InlineData("swagger/drinks-1/swagger.json")]
        public async Task Swagger_API_page_should_be_accessible(string apiPage)
        {
            var client = factory.CreateClient();

            var response = await client.GetAsync(apiPage);

            response.EnsureSuccessStatusCode();
            Assert.Equal("application/json; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
        }
    }
}
