using System.Collections.Generic;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;

namespace Project.API.WebApi.Swagger
{
    internal static class Extensions
    {
        public static IServiceCollection AddCustomSwagger(this IServiceCollection services)
        {
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc(AvailableDocuments.Ordering,
                    new OpenApiInfo { Title = "Drinks Ordering API", Version = "v1" });
                c.SwaggerDoc(AvailableDocuments.Servicing,
                    new OpenApiInfo { Title = "Drinks Servicing API", Version = "v1" });
                c.SwaggerDoc(AvailableDocuments.Infrastructure,
                    new OpenApiInfo { Title = "Drinks Infrastructure API", Version = "v1" });

                c.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
                {
                    Type = SecuritySchemeType.ApiKey,
                    In = ParameterLocation.Header,
                    Scheme = "Bearer",
                    BearerFormat = "JWT",
                    Name = "Authorization",
                    Description = @"JWT Authorization header using the Bearer scheme.
                      Enter 'Bearer' [space] and then your token in the text input below.
                      Example: 'Bearer 12345abcdef'",
                });

                c.OperationFilter<SecurityRequirementsOperationFilter>();
            });

            return services;
        }

        public static IApplicationBuilder UseCustomSwagger(
            this IApplicationBuilder app,
            IWebHostEnvironment environment
        )
        {
            app.UseSwagger(c =>
            {
                c.PreSerializeFilters.Add((swagger, httpReq) =>
                {
                    var servers = new List<OpenApiServer>();

                    if (environment.IsDevelopment())
                    {
                        servers.Add(new OpenApiServer { Url = $"http://{httpReq.Host.Value}" });
                    }
                    else
                    {
                        servers.Add(new OpenApiServer { Url = $"https://{httpReq.Host.Value}" });
                    }

                    swagger.Servers = servers;
                });
            });
            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint($"/swagger/{AvailableDocuments.Ordering}/swagger.json",
                    "Drinks Ordering API v1");
                c.SwaggerEndpoint($"/swagger/{AvailableDocuments.Servicing}/swagger.json",
                    "Drinks Servicing API v1");
                c.SwaggerEndpoint($"/swagger/{AvailableDocuments.Infrastructure}/swagger.json",
                    "Drinks Infrastructure API v1");
            });

            return app;
        }
    }
}