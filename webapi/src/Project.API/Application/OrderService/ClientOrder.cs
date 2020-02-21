using System.Collections.Generic;
using Project.API.Domain.AddIns;
using Project.API.Domain.Drinks;

namespace Project.API.Application.OrderService
{
    public sealed class ClientOrder
    {
        public List<OrderLine> Drinks { get; set; } = new List<OrderLine>();

        public sealed class OrderLine
        {
            public DrinkId DrinkId { get; set; } = default!;

            public DrinkSizeId DrinkSizeId { get; set; } = default!;

            public List<AddInId>? AddInIds { get; set; }
        }
    }
}