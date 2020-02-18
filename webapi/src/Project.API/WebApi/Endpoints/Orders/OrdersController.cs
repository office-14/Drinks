using System;
using Microsoft.AspNetCore.Mvc;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.Orders
{
    [Route("api/orders")]
    public class OrdersController : ControllerBase
    {
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