using System.Threading.Tasks;
using Project.API.WebApi.Swagger;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Infrastructure
{
    public class SwaggerTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public SwaggerTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Swagger_UI_page_should_be_accessible_for_anonymous_user()
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync("swagger/index.html");

            response.EnsureSuccessStatusCode();
            Assert.Equal("text/html; charset=utf-8",
                response.Content.Headers.ContentType.ToString());
        }

        [Theory]
        [InlineData(AvailableDocuments.Ordering)]
        [InlineData(AvailableDocuments.Servicing)]
        [InlineData(AvailableDocuments.Infrastructure)]
        public async Task Swagger_API_page_should_be_accessible_for_anonymous_user(string apiPage)
        {
            var client = factory.CreateAnonymous();

            var response = await client.GetAsync($"swagger/{apiPage}/swagger.json");

            response.EnsureSuccessResponse();
        }
    }
}
