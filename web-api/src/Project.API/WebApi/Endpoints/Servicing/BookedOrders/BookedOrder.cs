using System;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;

namespace Project.API.WebApi.Endpoints.Servicing.BookedOrders
{
    public class BookedOrder
    {
        [JsonPropertyName("id")]
        [Required]
        public int Id { get; set; }

        [JsonPropertyName("order_number")]
        [Required]
        public string OrderNumber { get; set; } = default!;

        [JsonPropertyName("total_price")]
        [Required]
        public int TotalPrice { get; set; }

        [JsonPropertyName("items")]
        [Required]
        public BookedItem[] Items { get; set; } = Array.Empty<BookedItem>();

        public static BookedOrder From(API.Servicing.Application.BookedOrders.BookedOrder bookedOrder)
            => new BookedOrder
            {
                Id = bookedOrder.Id.Value,
                OrderNumber = bookedOrder.OrderNumber.Value,
                TotalPrice = bookedOrder.TotalPrice.Amount,
                Items = bookedOrder.Items.Select(BookedItem.From).ToArray()
            };
    }
}