using System;
using System.Diagnostics.CodeAnalysis;
using Project.API.Domain.Core;
using Project.API.Domain.Orders.Exceptions;

namespace Project.API.Domain.Orders
{
    public sealed class Order : IEquatable<Order>
    {
        private Order(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status
        ) => (Id, OrderNumber, TotalPrice, Status) = (id, orderNumber, totalPrice, status);

        public OrderId Id { get; private set; }

        public OrderNumber OrderNumber { get; private set; }

        public Roubles TotalPrice { get; private set; }

        public Status Status { get; private set; }

        public void Finish()
        {
            if (Status == Status.Ready)
                throw CannotFinishOrder.becauseOrderIsAlreadyFinished(Id);

            Status = Status.Ready;
        }

        public bool Equals([AllowNull] Order other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Id.Equals(other.Id);
        }

        public override bool Equals(object? obj) => Equals(obj as Order);

        public override int GetHashCode() => Id.GetHashCode();

        public static Order New(OrderNumber orderNumber, Roubles totalPrice) =>
            new Order(default, orderNumber, totalPrice, Status.Cooking);

        public static Order Existing(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status
        ) => new Order(id, orderNumber, totalPrice, status);
    }
}