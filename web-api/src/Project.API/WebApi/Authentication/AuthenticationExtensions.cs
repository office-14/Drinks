using System;
using System.Collections.Generic;
using System.Security.Claims;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Project.API.Ordering.Domain.Users;

namespace Project.API.WebApi.Authentication
{
    public static class AuthenticationExtensions
    {
        public static IServiceCollection AddFirebaseAuthentication(this IServiceCollection services, IConfiguration configuration)
        {
            services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
                .AddJwtBearer(options =>
                {
                    options.Authority = configuration["Oidc:Authority"];
                    options.TokenValidationParameters = new Microsoft.IdentityModel.Tokens.TokenValidationParameters
                    {
                        ValidateIssuer = true,
                        ValidIssuer = configuration["Oidc:Issuer"],
                        ValidateAudience = true,
                        ValidAudience = configuration["Oidc:Audience"],
                        ValidateLifetime = true
                    };
                    options.Events = new JwtBearerEvents
                    {
                        // TODO: maybe it is better to use Firebase SDK to parse claims?
                        OnTokenValidated = ctx =>
                        {
                            var firebaseClaim = ctx.Principal.FindFirst("firebase");

                            if (firebaseClaim == null)
                            {
                                AddClientRole(ctx.Principal);
                                return Task.CompletedTask;
                            }

                            var details = ParseFirebaseDetails(firebaseClaim.Value);

                            if (details?.SignInProvider?.Equals("password", StringComparison.InvariantCultureIgnoreCase) == true)
                            {
                                AddAdminRole(ctx.Principal);
                            }
                            else
                            {
                                AddClientRole(ctx.Principal);
                            }

                            return Task.CompletedTask;
                        }
                    };
                });

            return services;
        }

        private static FirebaseDetails? ParseFirebaseDetails(string firebaseJson)
        {
            try
            {
                return JsonSerializer.Deserialize<FirebaseDetails>(firebaseJson);
            }
            catch (Exception)
            {
                return null;
            }
        }

        private static void AddRole(ClaimsPrincipal principal, string role)
        {
            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.Role, role)
            };

            var identity = new ClaimsIdentity(claims);
            principal.AddIdentity(identity);
        }

        private static void AddClientRole(ClaimsPrincipal principal)
        {
            AddRole(principal, Role.Client);
        }

        private static void AddAdminRole(ClaimsPrincipal principal)
        {
            AddRole(principal, Role.Administrator);
        }

        private sealed class FirebaseDetails
        {
            [JsonPropertyName("sign_in_provider")]
            public string SignInProvider { get; set; } = string.Empty;
        }
    }
}