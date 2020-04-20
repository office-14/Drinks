using Project.API.Ordering.Domain.Orders;
using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.LastUserOrder
{
    public sealed class LastOrderDetails
    {
        private LastOrderDetails(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            Comment comment
        ) =>
            (Id, OrderNumber, TotalPrice, Status, Comment) =
            (id, orderNumber, totalPrice, status, comment);

        public OrderId Id { get; }

        public OrderNumber OrderNumber { get; }

        public Roubles TotalPrice { get; }

        public Status Status { get; }

        public Comment Comment { get; }

        public static LastOrderDetails Available(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            Comment comment
        ) => new LastOrderDetails(
            id,
            orderNumber,
            totalPrice,
            status,
            comment
        );
    }
}