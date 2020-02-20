using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Application.OrderService;

namespace Project.API.WebApi.Endpoints.FinishOrder
{
    public class FinishOrderController : ControllerBase
    {
        private readonly OrderService orderService;

        public FinishOrderController(OrderService orderService)
        {
            this.orderService = orderService;
        }

        [HttpPost("/api/drinks/{id}/finish")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        public async Task<IActionResult> FinishOrderWithId([FromRoute] int id)
        {
            await orderService.FinishOrder(id);

            return NoContent();
        }
    }
}