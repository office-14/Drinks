using System;
using System.Collections.Generic;
using System.Security.Claims;
using System.Text.Encodings.Web;
using System.Text.Json;
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

            var credentials = JsonSerializer.Deserialize<Credentials>(token);
            var principal = CreateUser(credentials);
            var ticket = new AuthenticationTicket(principal, TestScheme);
            var result = AuthenticateResult.Success(ticket);

            return Task.FromResult(result);
        }

        private static ClaimsPrincipal CreateUser(Credentials credentials)
        {
            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.NameIdentifier, credentials.Id),
                new Claim(ClaimTypes.Role, credentials.IsAdmin ? "admin" : "client")
            };

            var identity = new ClaimsIdentity(claims, TestScheme);
            var principal = new ClaimsPrincipal(identity);
            return principal;
        }

        internal sealed class Credentials
        {
            public string Id { get; set; }

            public bool IsAdmin { get; set; }
        }
    }
}