using System.Threading.Tasks;
using Project.API.WebApi.Endpoints.Infrastructure.DeviceTokens;
using Project.IntegrationTests.Configuration;
using Xunit;

namespace Project.IntegrationTests.Notifications
{
    public class UpdateDeviceTokenTests
        : IClassFixture<ApiWebApplicationFactory>
    {
        private readonly ApiWebApplicationFactory factory;

        public UpdateDeviceTokenTests(ApiWebApplicationFactory factory)
        {
            this.factory = factory;
        }

        [Fact]
        public async Task Anonymous_user_cannot_update_device_tokens()
        {
            var client = factory.CreateAnonymous();

            var response = await client.PostContentAsync<UpdateDeviceToken>(
                "api/user/device-tokens/update",
                new UpdateDeviceToken
                {
                    DeviceId = "device-1",
                    Token = "token-1"
                }
            );

            response.EnsureUnauthenticated();
        }

        [Fact]
        public async Task Admin_user_cannot_update_device_tokens()
        {
            var admin = factory.CreateAdminUser();

            var response = await admin.PostContentAsync<UpdateDeviceToken>(
                "api/user/device-tokens/update",
                new UpdateDeviceToken
                {
                    DeviceId = "device-1",
                    Token = "token-1"
                }
            );

            response.EnsureUnauthorized();
        }

        [Fact]
        public async Task Client_user_can_update_device_tokens()
        {
            var client = factory.CreateClientUser();

            var response = await client.PostContentAsync<UpdateDeviceToken>(
                "api/user/device-tokens/update",
                new UpdateDeviceToken
                {
                    DeviceId = "device-1",
                    Token = "token-1"
                }
            );

            response.EnsureSuccessStatusCode();
        }
    }
}