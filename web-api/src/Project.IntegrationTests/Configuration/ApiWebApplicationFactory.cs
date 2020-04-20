using System.Net.Http;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Project.API.WebApi;

namespace Project.IntegrationTests.Configuration
{
    public sealed class ApiWebApplicationFactory : WebApplicationFactory<Startup>
    {
        protected override void ConfigureWebHost(IWebHostBuilder builder)
        {
            builder.ConfigureLogging((host, logging) =>
            {
                // logging.ClearProviders();
            });

            builder.ConfigureTestServices(services =>
            {
                services.AddAuthentication(TestAuthHandler.TestScheme)
                            .AddScheme<AuthenticationSchemeOptions, TestAuthHandler>(
                                TestAuthHandler.TestScheme, options => { });
            });
        }
    }
}