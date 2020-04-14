using System;
using System.Security.Claims;
using System.Text.Encodings.Web;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.Net.Http.Headers;

namespace Project.IntegrationTests.Configuration
{
    internal sealed class TestAuthHandler : AuthenticationHandler<AuthenticationSchemeOptions>
    {
        internal static readonly string TestScheme = "Test";

        public TestAuthHandler(IOptionsMonitor<AuthenticationSchemeOptions> options, ILoggerFactory logger, UrlEncoder encoder, ISystemClock clock)
            : base(options, logger, encoder, clock)
        {
        }

        protected override Task<AuthenticateResult> HandleAuthenticateAsync()
        {
            string authorization = this.Request.Headers[HeaderNames.Authorization];

            // If no authorization header found, nothing to process further
            if (string.IsNullOrEmpty(authorization))
            {
                return Task.FromResult(AuthenticateResult.NoResult());
            }

            string token = string.Empty;

            if (authorization.StartsWith($"{TestScheme} ", StringComparison.OrdinalIgnoreCase))
            {
                token = authorization.Substring($"{TestScheme} ".Length).Trim();
            }

            if (string.IsNullOrWhiteSpace(token))
            {
                return Task.FromResult(AuthenticateResult.NoResult());
            }

            ClaimsPrincipal principal = default;

            switch (token)
            {
                case "Alice":
                    principal = FindAlice();
                    break;

                case "Bob":
                    principal = FindBob();
                    break;

                case "Zod":
                    principal = FindZod();
                    break;

                default:
                    return Task.FromResult(AuthenticateResult.NoResult());
            }

            var ticket = new AuthenticationTicket(principal, TestScheme);
            var result = AuthenticateResult.Success(ticket);

            return Task.FromResult(result);
        }

        private static ClaimsPrincipal FindAlice()
        {
            var claims = new[]
            {
                new Claim(ClaimTypes.Name, "Alice"),
                new Claim(ClaimTypes.NameIdentifier, Guid.NewGuid().ToString())
            };
            var identity = new ClaimsIdentity(claims, TestScheme);
            var principal = new ClaimsPrincipal(identity);
            return principal;
        }

        private static ClaimsPrincipal FindBob()
        {
            var claims = new[]
            {
                new Claim(ClaimTypes.Name, "Bob"),
                new Claim(ClaimTypes.NameIdentifier, Guid.NewGuid().ToString())
            };
            var identity = new ClaimsIdentity(claims, TestScheme);
            var principal = new ClaimsPrincipal(identity);
            return principal;
        }

        private static ClaimsPrincipal FindZod()
        {
            var claims = new[]
            {
                new Claim(ClaimTypes.Name, "Bob"),
                new Claim(ClaimTypes.NameIdentifier, Guid.NewGuid().ToString())
            };
            var identity = new ClaimsIdentity(claims, TestScheme);
            var principal = new ClaimsPrincipal(identity);
            return principal;
        }
    }
}