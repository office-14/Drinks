using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Domain.Users;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Infrastructure.DeviceTokens
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Infrastructure)]
    [Authorize(Roles = Role.Client)]
    [Route("api/user/device-tokens")]
    public class DeviceTokensController : ControllerBase
    {
        [HttpPost]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public void UpdateUserDevice([FromBody] UpdateDeviceToken body)
        {
        }

        [HttpDelete]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public void UpdateUserDevice([FromBody] DeleteDeviceToken body)
        {
        }
    }
}