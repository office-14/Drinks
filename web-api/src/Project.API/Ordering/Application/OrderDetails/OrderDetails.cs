using System;
using Project.API.Ordering.Domain.Core;
using Project.API.Ordering.Domain.Orders;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.OrderDetails
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