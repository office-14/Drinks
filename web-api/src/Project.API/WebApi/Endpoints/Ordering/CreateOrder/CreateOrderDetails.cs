using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using Project.API.Ordering.Application.OrderService;
using Project.API.Ordering.Domain.Drinks;

namespace Project.API.WebApi.Endpoints.Ordering.CreateOrder
{
    public class CreateOrderDetails
    {
        [JsonPropertyName("drinks")]
        [Required]
        public List<CreateOrderDrinksItem>? Drinks { get; set; }

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
        public List<int>? AddIns { get; set; }

        public ClientOrder.OrderLine AsClientOrderLine()
        {
            var line = new ClientOrder.OrderLine();

            line.DrinkId = API.Ordering.Domain.Drinks.DrinkId.From(DrinkId);
            line.DrinkSizeId = API.Ordering.Domain.Drinks.DrinkSizeId.From(SizeId);
            line.AddInIds = AddIns?.Select(AddInId.From).ToList() ?? new List<AddInId>();

            return line;
        }
    }
}