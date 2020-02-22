using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Domain.Orders;

namespace Project.API.WebApi.Endpoints.FinishOrder
{
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
        [ProducesResponseType(StatusCodes.Status404NotFound)]
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