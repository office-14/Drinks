using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Project.API.Ordering.Application.DrinkDetails;
using Project.API.Ordering.Application.OrderService;
using Project.API.Ordering.Domain.Drinks;
using Project.API.Ordering.Domain.Orders;
using Project.API.Infrastructure.Repositories;
using Project.API.WebApi.Swagger;
using Project.API.Servicing.Application.BookedOrders;
using Microsoft.Extensions.Configuration;
using Project.API.WebApi.MediatR;
using Project.API.Infrastructure.Firebase;
using Project.API.Servicing.Application.FinishOrder;
using Project.API.Ordering.Application.LastUserOrder;
using Project.API.Infrastructure.Repositories.LastOrders;
using Project.API.WebApi.Authentication;
using Project.API.Infrastructure.Notifications;
using Project.API.WebApi.Cors;

[assembly: ApiController]
namespace Project.API.WebApi
{
    public class Startup
    {
        public Startup(IConfiguration config, IWebHostEnvironment env) =>
            (Configuration, Environment) = (config, env);

        public IConfiguration Configuration { get; }

        public IWebHostEnvironment Environment { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvcCore()
                .AddApiExplorer();

            services.AddCustomCors(Configuration);

            services.AddFirebaseAuthentication(Configuration);

            services.AddAuthorization();

            services.AddCustomSwagger();
            services.AddCustomMediatR();

            services.AddSingleton<InMemoryDrinksRepository>();
            services.AddSingleton<IDrinksRepository>(provider => provider.GetRequiredService<InMemoryDrinksRepository>());
            services.AddSingleton<IDrinkSizesRepository>(provider => provider.GetRequiredService<InMemoryDrinksRepository>());
            services.AddSingleton<IDrinkDetailsRepository>(provider => provider.GetRequiredService<InMemoryDrinksRepository>());
            services.AddSingleton<IAddInsRepository, InMemoryAddInsRepository>();
            services.AddSingleton<InMemoryOrdersRepository>();
            services.AddSingleton<IOrdersRepository>(provider => provider.GetRequiredService<InMemoryOrdersRepository>());
            services.AddSingleton<IBookedOrdersRepository>(provider => provider.GetRequiredService<InMemoryOrdersRepository>());
            services.AddSingleton<ILastUserOrderProvider, InMemoryLastUserOrders>();
            services.AddSingleton<LastUserOrders>();
            services.AddSingleton<CreateOrderService>();
            services.AddSingleton<OrderNumberProvider>();
            services.AddSingleton<OrderDraftFactory>();
            services.AddSingleton<OrderFactory>();
            services.AddSingleton<FinishOrderService>();
            services.AddSingleton<RegisteredDevices>();
            services.AddFirebaseNotifications();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, ILogger<Startup> logger)
        {
            app.UseExceptionHandler("/error");

            app.UseRouting();

            app.UseCustomCors(Environment, logger);

            app.UseAuthentication();
            app.UseAuthorization();

            app.UseCustomSwagger(Environment);

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
