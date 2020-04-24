using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Application.LastUserOrderStatus;
using Project.API.Ordering.Domain.Users;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Ordering.LastUserOrderStatus
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Ordering)]
    [Authorize(Roles = Role.Client)]
    public class LastUserOrderStatusController : ControllerBase
    {
        private readonly ILastUserOrderStatusProvider statusProvider;

        public LastUserOrderStatusController(ILastUserOrderStatusProvider statusProvider)
        {
            this.statusProvider = statusProvider;
        }

        [HttpGet("api/user/orders/last/status")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<LastOrderStatus>), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<ResponseWrapper<LastOrderStatus?>> GetLastOrder()
        {
            var user = this.DomainUser();
            var orderStatus = await statusProvider.StatusOfLastOrder(user);

            if (orderStatus == null) return ResponseWrapper<LastOrderStatus?>.From(null);
            return ResponseWrapper<LastOrderStatus?>.From(LastOrderStatus.From(orderStatus));
        }
    }
}