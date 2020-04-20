using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;
using Project.API.Ordering.Application.LastUserOrder;
using Project.API.Ordering.Application.OrderService;
using Project.API.Ordering.Domain.Users;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Ordering.CreateOrder
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Ordering)]
    [Authorize(Roles = Role.Client)]
    public class CreateOrderController : ControllerBase
    {
        private readonly CreateOrderService orderService;
        private readonly ILastUserOrderProvider lastUserOrders;

        public CreateOrderController(
            CreateOrderService orderService,
            ILastUserOrderProvider lastUserOrders
        )
        {
            this.orderService = orderService;
            this.lastUserOrders = lastUserOrders;
        }

        [HttpPost("api/orders")]
        [Consumes(MediaTypeNames.Application.Json)]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<CreatedOrder>), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]

        public async Task<ResponseWrapper<CreatedOrder>> createNewOrder(
            [FromBody, BindRequired] CreateOrderDetails orderDetails)
        {
            var createdOrderId = await orderService.CreateNewOrder(
                this.DomainUser(),
                orderDetails.AsClientOrder()
            );
            var orderToReturn = (await lastUserOrders.LastUserOrder(this.DomainUser()))!;

            return ResponseWrapper<CreatedOrder>.From(CreatedOrder.From(orderToReturn));
        }
    }
}