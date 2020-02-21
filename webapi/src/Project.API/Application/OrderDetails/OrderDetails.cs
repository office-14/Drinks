using System;
using Project.API.Domain.Core;
using Project.API.Domain.Orders;

namespace Project.API.Application.OrderDetails
{
    public sealed class OrderDetails
    {
        private OrderDetails(
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

        public static OrderDetails Available(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status
        ) => new OrderDetails(
            id,
            orderNumber,
            totalPrice,
            status
        );
    }
}