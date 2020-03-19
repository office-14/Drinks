using System.Reflection;
using MediatR;
using Microsoft.Extensions.DependencyInjection;

namespace Project.API.WebApi.MediatR
{
    internal static class Extensions
    {
        public static IServiceCollection AddCustomMediatR(this IServiceCollection services)
            => services.AddMediatR(Assembly.GetExecutingAssembly());
    }
}