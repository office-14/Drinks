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
using Microsoft.OpenApi.Models;
using Project.API.Application.OrderDetails;
using Project.API.Domain.AddIns;
using Project.API.Domain.Drinks;
using Project.API.Infrastructure.Repositories;
using Project.API.WebApi.Swagger;

[assembly: ApiController]
namespace Project.API.WebApi
{
    public class Startup
    {
        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvcCore()
                .AddApiExplorer();

            services.AddCors(options =>
            {
                options.AddPolicy("AllowAll", builder =>
                {
                    builder
                        .AllowAnyOrigin()
                        .AllowAnyHeader()
                        .AllowAnyMethod();
                });
            });

            services.AddCustomSwagger();

            services.AddSingleton<IDrinksRepository, InMemoryDrinksRepository>();
            services.AddSingleton<IDrinkSizesRepository, InMemoryDrinksRepository>();
            services.AddSingleton<IAddInsRepository, InMemoryAddInsRepository>();
            services.AddSingleton<IOrderDetailsRepository, InMemoryOrdersRepository>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            app.UseRouting();

            if (env.IsDevelopment())
            {
                app.UseCors("AllowAll");
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
