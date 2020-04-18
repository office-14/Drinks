using System.Net.Mime;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;
using Project.API.Infrastructure.Notifications;
using Project.API.Ordering.Domain.Users;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Infrastructure.DeviceTokens
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Infrastructure)]
    [Authorize(Roles = Role.Client)]
    [Route("api/user/device-tokens")]
    public class DeviceTokensController : ControllerBase
    {
        private readonly RegisteredDevices registry;

        public DeviceTokensController(RegisteredDevices registry)
        {
            this.registry = registry;
        }

        [HttpPost]
        [Consumes(MediaTypeNames.Application.Json)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public IActionResult UpdateUserDevice([FromBody, BindRequired] UpdateDeviceToken body)
        {
            var user = this.DomainUser();

            registry.UpdateToken(
                user.Id,
                DeviceId.From(body.DeviceId),
                DeviceToken.From(body.Token)
            );

            return NoContent();
        }

        [HttpDelete]
        [Consumes(MediaTypeNames.Application.Json)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public IActionResult DeleteUserDevice([FromBody, BindRequired] DeleteDeviceToken body)
        {
            var user = this.DomainUser();

            registry.RemoveToken(
                user.Id,
                DeviceId.From(body.DeviceId)
            );

            return NoContent();
        }
    }
}