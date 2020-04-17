using System.Security.Claims;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Domain.Users;

namespace Project.API.WebApi.Endpoints.Shared
{
    public static class ControllerBaseExtensions
    {
        // TODO: I didn't find better place to put to-domain-user conversion
        public static User DomainUser(this ControllerBase controller) =>
            FromPrincipal(controller.User);

        private static User FromPrincipal(ClaimsPrincipal principal)
        {
            var idClaim = principal.FindFirst(ClaimTypes.NameIdentifier);

            return User.New(UserId.From(idClaim.Value));
        }
    }
}