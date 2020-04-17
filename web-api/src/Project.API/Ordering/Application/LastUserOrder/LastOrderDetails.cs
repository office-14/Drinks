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
            Status status
        ) =>
            (Id, OrderNumber, TotalPrice, Status) =
            (id, orderNumber, totalPrice, status);

        public OrderId Id { get; }

        public OrderNumber OrderNumber { get; }

        public Roubles TotalPrice { get; }

        public Status Status { get; }

        public static LastOrderDetails Available(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status
        ) => new LastOrderDetails(
            id,
            orderNumber,
            totalPrice,
            status
        );
    }
}