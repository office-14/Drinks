using System.Collections.Generic;

namespace Project.API.Application.OrderService
{
    public sealed class ClientOrder
    {
        public List<OrderLine> Drinks { get; set; }

        public sealed class OrderLine
        {
            public int DrinkId { get; set; }

            public int DrinkSizeId { get; set; }

            public List<int> AddInIds { get; set; }
        }
    }
}