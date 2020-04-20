using System.Threading.Tasks;
using Project.API.WebApi.Endpoints.Infrastructure.DeviceTokens;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Notifications
{
    public class DeleteDeviceTokenTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public DeleteDeviceTokenTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Anonymous_user_cannot_delete_device_tokens()
        {
            var client = factory.CreateAnonymous();

            var response = await client.PostContentAsync<DeleteDeviceToken>(
                "api/user/device-tokens/delete",
                new DeleteDeviceToken
                {
                    DeviceId = "device-1",
                }
            );

            response.EnsureUnauthenticated();
        }

        [Fact]
        public async Task Admin_user_cannot_delete_device_tokens()
        {
            var admin = factory.CreateAdminUser();

            var response = await admin.PostContentAsync<DeleteDeviceToken>(
                "api/user/device-tokens/delete",
                new DeleteDeviceToken
                {
                    DeviceId = "device-1",
                }
            );

            response.EnsureUnauthorized();
        }

        [Fact]
        public async Task Client_user_can_delete_device_tokens()
        {
            var client = factory.CreateClientUser();

            var response = await client.PostContentAsync<DeleteDeviceToken>(
                "api/user/device-tokens/delete",
                new DeleteDeviceToken
                {
                    DeviceId = "device-1",
                }
            );

            response.EnsureSuccessStatusCode();
        }
    }
}