using System;
using Project.API.Domain.Core;
using Project.API.Domain.Orders.Exceptions;

namespace Project.API.Domain.Orders
{
    public sealed class Order
    {
        private Order() { }

        public int Id { get; private set; }

        public OrderNumber OrderNumber { get; private set; }

        public Roubles TotalPrice { get; private set; }

        public Status Status { get; private set; }

        public DateTime CreatedDate { get; private set; }

        public DateTime? FinishDate { get; private set; }

        public void Finish()
        {
            if (Status == Status.Ready)
                throw CannotFinishOrder.becauseOrderIsAlreadyFinished(Id);

            Status = Status.Ready;
            FinishDate = DateTime.UtcNow;
        }

        public static Order New(OrderNumber orderNumber, Roubles totalPrice)
        {
            var order = new Order();

            order.OrderNumber = orderNumber;
            order.TotalPrice = totalPrice;

            order.Status = Status.Cooking;
            order.CreatedDate = DateTime.UtcNow;

            return order;
        }

        public static Order Existing(
            int id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            DateTime createdDate,
            DateTime? finishDate
        )
        {
            var order = new Order();

            order.Id = id;
            order.OrderNumber = orderNumber;
            order.TotalPrice = totalPrice;
            order.Status = status;
            order.CreatedDate = createdDate;
            order.FinishDate = finishDate;

            return order;
        }


    }
}