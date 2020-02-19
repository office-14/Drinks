using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.OpenApi.Models;
using Project.API.Application;
using Project.API.Application.DrinkDetails;
using Project.API.Application.OrderDetails;
using Project.API.Application.OrderService;
using Project.API.Domain.AddIns;
using Project.API.Domain.Drinks;
using Project.API.Domain.Orders;
using Project.API.Infrastructure.Repositories;
using Project.API.WebApi.Swagger;

[assembly: ApiController]
namespace Project.API.WebApi
{
    public class Startup
    {
        private static readonly string CorsAllowAllPolicy = "AllowAll";

        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvcCore()
                .AddApiExplorer();

            services.AddCors(options =>
            {
                options.AddPolicy(CorsAllowAllPolicy, builder =>
                {
                    builder
                        .AllowAnyOrigin()
                        .AllowAnyHeader()
                        .AllowAnyMethod();
                });
            });

            services.AddCustomSwagger();

            services.AddSingleton<InMemoryDrinksRepository>();
            services.AddSingleton<IDrinksRepository>(provider => provider.GetRequiredService<InMemoryDrinksRepository>());
            services.AddSingleton<IDrinkSizesRepository>(provider => provider.GetRequiredService<InMemoryDrinksRepository>());
            services.AddSingleton<IDrinkDetailsRepository>(provider => provider.GetRequiredService<InMemoryDrinksRepository>());
            services.AddSingleton<IAddInsRepository, InMemoryAddInsRepository>();
            services.AddSingleton<InMemoryOrdersRepository>();
            services.AddSingleton<IOrderDetailsRepository>(provider => provider.GetRequiredService<InMemoryOrdersRepository>());
            services.AddSingleton<IOrdersRepository>(provider => provider.GetRequiredService<InMemoryOrdersRepository>());
            services.AddSingleton<OrderService>();
            services.AddSingleton<OrderNumberProvider>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(
            IApplicationBuilder app,
            IWebHostEnvironment env,
            ILogger<Startup> logger
        )
        {
            app.UseRouting();

            if (env.IsDevelopment())
            {
                logger.LogInformation("CORS: allowing all requests");
                app.UseCors(CorsAllowAllPolicy);
            }
            else
            {
                app.UseCors();
            }

            app.UseCustomSwagger();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
