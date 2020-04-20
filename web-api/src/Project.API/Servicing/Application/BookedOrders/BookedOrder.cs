using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Servicing.Application.BookedOrders
{
    public sealed class BookedOrder
    {
        private BookedOrder(
            OrderId orderId,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Comment comment,
            BookedItem[] items
        ) =>
            (Id, OrderNumber, TotalPrice, Items, Comment) =
            (orderId, orderNumber, totalPrice, items, comment);

        public OrderId Id { get; }

        public OrderNumber OrderNumber { get; }

        public Roubles TotalPrice { get; }

        public Comment Comment { get; }

        public BookedItem[] Items { get; }

        public static BookedOrder Available(
            OrderId orderId,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Comment comment,
            BookedItem[] items
        ) => new BookedOrder(
            orderId,
            orderNumber,
            totalPrice,
            comment,
            items
        );
    }
}