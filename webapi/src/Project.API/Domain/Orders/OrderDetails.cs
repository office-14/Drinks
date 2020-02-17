using System;
using Project.API.Domain.Core;

namespace Project.API.Domain.Orders
{
    public sealed class OrderDetails
    {
        private OrderDetails(
            int id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            DateTime createdDate,
            DateTime? finishDate
        )
        {
            Id = id;
            OrderNumber = orderNumber;
            TotalPrice = totalPrice;
            Status = status;
            CreatedDate = createdDate;
            FinishDate = finishDate;
        }

        public int Id { get; }

        public OrderNumber OrderNumber { get; }

        public Roubles TotalPrice { get; }

        public Status Status { get; }

        public DateTime CreatedDate { get; }

        public DateTime? FinishDate { get; }

        public static OrderDetails Available(
            int id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            DateTime createdDate,
            DateTime? finishDate = null // TODO: maybe use method overloading
        ) => new OrderDetails(
            id,
            orderNumber,
            totalPrice,
            status,
            createdDate,
            finishDate
        );
    }
}