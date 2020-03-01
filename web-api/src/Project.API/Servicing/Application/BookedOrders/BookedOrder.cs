using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Servicing.Application.BookedOrders
{
    public sealed class BookedOrder
    {
        private BookedOrder(OrderId orderId) => (Id) = (orderId);

        public OrderId Id { get; set; }

        public static BookedOrder Available(OrderId orderId) => new BookedOrder(orderId);
    }
}