using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Domain.Users;
using Project.API.Servicing.Application.FinishOrder;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Servicing.FinishOrder
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Servicing)]
    [Authorize(Roles = Role.Administrator)]
    public class FinishOrderController : ControllerBase
    {
        private readonly FinishOrderService finishOrderService;

        public FinishOrderController(FinishOrderService finishOrderService)
            => this.finishOrderService = finishOrderService;

        [HttpPost("/api/orders/{id}/finish")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> FinishOrderWithId([FromRoute] int id)
        {
            if (!(await finishOrderService.FinishOrderWithId(OrderId.From(id))))
            {
                return NotFound();
            }

            return NoContent();
        }
    }
}