using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Application.OrderDetails;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.OrderWithId
{
    public class OrderWithIdController : ControllerBase
    {
        private readonly IOrderDetailsRepository orderDetailsRepository;

        public OrderWithIdController(IOrderDetailsRepository orderDetailsRepository)
        {
            this.orderDetailsRepository = orderDetailsRepository;
        }

        [HttpGet("api/orders/{id}")]
        public async Task<ActionResult<ResponseWrapper<OrderWithId>>> Get([FromRoute] int id)
        {
            var order = await orderDetailsRepository.GetOrderDetailsById(id);

            if (order == null)
            {
                return NotFound();
            }

            return ResponseWrapper<OrderWithId>.From(OrderWithId.From(order));
        }
    }
}