using System.Collections.Generic;
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
            Comment comment,
            List<LastOrderDrink> drinks
        ) =>
            (Id, OrderNumber, TotalPrice, Status, Comment, Drinks) =
            (id, orderNumber, totalPrice, status, comment, drinks);

        public OrderId Id { get; }

        public OrderNumber OrderNumber { get; }

        public Roubles TotalPrice { get; }

        public Status Status { get; }

        public Comment Comment { get; }

        public List<LastOrderDrink> Drinks { get; }

        public static LastOrderDetails Available(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            Comment comment,
            List<LastOrderDrink> drinks
        ) => new LastOrderDetails(
            id,
            orderNumber,
            totalPrice,
            status,
            comment,
            drinks
        );
    }
}