using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using Project.API.Application;
using Project.API.Application.OrderService;

namespace Project.API.WebApi.Endpoints.CreateOrder
{
    public class CreateOrderDetails
    {
        [JsonPropertyName("drinks")]
        [Required]
        public List<CreateOrderDrinksItem> Drinks { get; set; }

        public ClientOrder AsClientOrder()
        {
            var clientOrder = new ClientOrder();

            clientOrder.Drinks =
                Drinks?.Select(item => item.AsClientOrderLine()).ToList() ??
                new List<ClientOrder.OrderLine>();

            return clientOrder;
        }
    }

    public class CreateOrderDrinksItem
    {
        [JsonPropertyName("drink_id")]
        [Required]
        public int DrinkId { get; set; }

        [JsonPropertyName("size_id")]
        [Required]
        public int SizeId { get; set; }

        [JsonPropertyName("add-ins")]
        public List<int> AddIns { get; set; }

        public ClientOrder.OrderLine AsClientOrderLine()
        {
            var line = new ClientOrder.OrderLine();

            line.DrinkId = DrinkId;
            line.DrinkSizeId = SizeId;
            line.AddInIds = AddIns ?? new List<int>();

            return line;
        }
    }
}