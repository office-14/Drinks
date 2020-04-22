using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;

namespace Project.API.WebApi.Cors
{
    public static class CorsExtensions
    {
        private static readonly string CorsAllowAllPolicy = "AllowAll";

        private static readonly string CorsAllowOnlyWebSites = "AllowOnlyWebSites";

        public static IServiceCollection AddCustomCors(
            this IServiceCollection services,
            IConfiguration configuration
        )
        {
            services.AddCors(options =>
            {
                options.AddPolicy(CorsAllowAllPolicy, builder =>
                {
                    builder
                        .AllowAnyOrigin()
                        .AllowAnyHeader()
                        .AllowAnyMethod();
                });

                options.AddPolicy(CorsAllowOnlyWebSites, builder =>
                {
                    var origins = configuration.GetSection("Cors:AllowedOrigins")
                        .Get<string[]>() ?? new string[0];

                    builder.WithOrigins(origins)
                        .AllowAnyHeader()
                        .AllowAnyMethod();
                });
            });

            return services;
        }

        public static IApplicationBuilder UseCustomCors(
            this IApplicationBuilder app,
            IWebHostEnvironment environment,
            ILogger logger
        )
        {
            if (environment.IsDevelopment())
            {
                logger.LogInformation("CORS: allowing all requests");
                app.UseCors(CorsAllowAllPolicy);
            }
            else
            {
                app.UseCors(CorsAllowOnlyWebSites);
            }

            return app;
        }
    }
}