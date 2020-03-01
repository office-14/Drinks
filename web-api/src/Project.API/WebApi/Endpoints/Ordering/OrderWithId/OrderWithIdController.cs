using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Project.API.Ordering.Application.OrderDetails;
using Project.API.SharedKernel.Domain.Orders;
using Project.API.WebApi.Endpoints.Ordering.Shared;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Ordering.OrderWithId
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Ordering)]
    public class OrderWithIdController : ControllerBase
    {
        private readonly IOrderDetailsRepository orderDetailsRepository;
        private readonly ILogger<OrderWithIdController> logger;

        public OrderWithIdController(
            IOrderDetailsRepository orderDetailsRepository,
            ILogger<OrderWithIdController> logger
        )
        {
            this.orderDetailsRepository = orderDetailsRepository;
            this.logger = logger;
        }

        [HttpGet("api/orders/{id}")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<SingleOrder>), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<ResponseWrapper<SingleOrder>>> Get([FromRoute] int id)
        {
            var order = await orderDetailsRepository.OrderDetailsWithId(OrderId.From(id));

            if (order == null)
            {
                logger.LogWarning("Trying to find order with id='{id}' returns null", id);
                return NotFound();
            }

            return ResponseWrapper<SingleOrder>.From(SingleOrder.From(order));
        }
    }
}