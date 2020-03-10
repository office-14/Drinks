using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Domain.Orders;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Servicing.FinishOrder
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Servicing)]
    [Authorize]
    public class FinishOrderController : ControllerBase
    {
        private readonly IOrdersRepository ordersRepository;

        public FinishOrderController(IOrdersRepository ordersRepository)
        {
            this.ordersRepository = ordersRepository;
        }

        [HttpPost("/api/orders/{id}/finish")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> FinishOrderWithId([FromRoute] int id)
        {
            var order = await ordersRepository.OrderWithId(OrderId.From(id));

            if (order == null) return NotFound();

            order.Finish();

            await ordersRepository.Save(order);

            return NoContent();
        }
    }
}