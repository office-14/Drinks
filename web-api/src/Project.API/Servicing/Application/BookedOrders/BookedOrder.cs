using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Servicing.Application.BookedOrders
{
    public sealed class BookedOrder
    {
        private BookedOrder(
            OrderId orderId,
            OrderNumber orderNumber,
            Roubles totalPrice
        ) =>
            (Id, OrderNumber, TotalPrice) =
            (orderId, orderNumber, totalPrice);

        public OrderId Id { get; }

        public OrderNumber OrderNumber { get; }

        public Roubles TotalPrice { get; }

        public static BookedOrder Available(
            OrderId orderId,
            OrderNumber orderNumber,
            Roubles totalPrice
        ) => new BookedOrder(
            orderId,
            orderNumber,
            totalPrice
        );
    }
}