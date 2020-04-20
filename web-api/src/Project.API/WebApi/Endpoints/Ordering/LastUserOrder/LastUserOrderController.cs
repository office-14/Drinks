using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Application.LastUserOrder;
using Project.API.Ordering.Domain.Users;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Ordering.LastUserOrder
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Ordering)]
    [Authorize(Roles = Role.Client)]
    public class LastUserOrderController : ControllerBase
    {
        private readonly ILastUserOrderProvider lastUserOrderProvider;

        public LastUserOrderController(
            ILastUserOrderProvider lastUserOrderProvider
        )
        {
            this.lastUserOrderProvider = lastUserOrderProvider;
        }

        [HttpGet("api/user/orders/last")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<LastOrder>), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<ResponseWrapper<LastOrder?>> GetLastOrder()
        {
            var user = this.DomainUser();
            var lastOrder = await lastUserOrderProvider.LastUserOrder(user);

            if (lastOrder == null) return ResponseWrapper<LastOrder?>.From(null);
            return ResponseWrapper<LastOrder?>.From(LastOrder.From(lastOrder));
        }
    }
}