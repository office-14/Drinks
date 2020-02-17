using System;
using Microsoft.AspNetCore.Mvc;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.Orders
{
    [Route("api/orders")]
    public class OrdersController : ControllerBase
    {
        [HttpGet("{id}")]
        public ResponseWrapper<OrderItem> OrderWithId([FromRoute] int id)
        {
            return ResponseWrapper<OrderItem>.From(new OrderItem
            {
                Id = id,
                StatusCode = "FINISHED",
                StatusName = "Finished",
                CreatedDate = DateTime.UtcNow.AddMinutes(-10).ToString("o"),
                FinishDate = DateTime.UtcNow.ToString("o"),
                OrderNumber = "ORD-101",
                TotalPrice = 520
            });
        }

        [HttpPost]
        public ResponseWrapper<CreatedOrderItem> createNewOrder([FromBody] CreateOrderDetails orderDetails)
        {
            return ResponseWrapper<CreatedOrderItem>.From(new CreatedOrderItem
            {
                Id = 102,
                StatusCode = "COOKING",
                StatusName = "Cooking",
                CreatedDate = DateTime.UtcNow.ToString("o"),
                OrderNumber = "ORD-102",
                TotalPrice = 220
            });
        }
    }
}