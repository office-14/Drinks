using System;
using Project.API.Domain.Core;
using Project.API.Domain.Orders;

namespace Project.API.Application.OrderDetails
{
    public sealed class OrderDetails
    {
        private OrderDetails(
            int id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status
        )
        {
            Id = id;
            OrderNumber = orderNumber;
            TotalPrice = totalPrice;
            Status = status;
        }

        public int Id { get; }

        public OrderNumber OrderNumber { get; }

        public Roubles TotalPrice { get; }

        public Status Status { get; }

        public static OrderDetails Available(
            int id,
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