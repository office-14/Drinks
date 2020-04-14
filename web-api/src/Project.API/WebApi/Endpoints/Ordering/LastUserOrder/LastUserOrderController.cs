using System.Net.Mime;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.WebApi.Endpoints.Ordering.Shared;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Ordering.LastUserOrder
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Ordering)]
    [Authorize]
    public class LastUserOrderController : ControllerBase
    {
        [HttpGet("api/user/orders/last")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<SingleOrder>), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public ResponseWrapper<SingleOrder> GetLastOrder()
        {
            return new ResponseWrapper<SingleOrder>
            {
            };
        }
    }
}