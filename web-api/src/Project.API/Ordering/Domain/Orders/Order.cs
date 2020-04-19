using System;
using System.Diagnostics.CodeAnalysis;
using Project.API.Ordering.Domain.Orders.Exceptions;
using Project.API.Ordering.Domain.Users;
using Project.API.SharedKernel.Domain.Core;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Domain.Orders
{
    public sealed class Order : IEquatable<Order>
    {
        private Order(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            OrderDraft draft,
            UserId clientId
        ) =>
            (Id, OrderNumber, TotalPrice, Status, Draft, ClientId) =
            (id, orderNumber, totalPrice, status, draft, clientId);

        public OrderId Id { get; private set; }

        public OrderNumber OrderNumber { get; private set; }

        public Roubles TotalPrice { get; private set; }

        public Status Status { get; private set; }

        public OrderDraft Draft { get; private set; }

        public UserId ClientId { get; private set; }

        public void Finish()
        {
            if (IsFinished())
                throw CannotFinishOrder.BecauseOrderIsAlreadyFinished(Id);

            Status = Status.Ready;
        }

        public bool IsFinished() => Status == Status.Ready;

        public bool Equals([AllowNull] Order other)
        {
            if (ReferenceEquals(this, other)) return true;
            if (ReferenceEquals(null, other)) return false;

            return Id.Equals(other.Id);
        }

        public override bool Equals(object? obj) => Equals(obj as Order);

        public override int GetHashCode() => Id.GetHashCode();

        public static Order New(
            OrderNumber orderNumber,
            Roubles totalPrice,
            OrderDraft draft,
            UserId clientId
        ) => new Order(
            default,
            orderNumber,
            totalPrice,
            Status.Cooking,
            draft,
            clientId
        );

        public static Order Existing(
            OrderId id,
            OrderNumber orderNumber,
            Roubles totalPrice,
            Status status,
            OrderDraft draft,
            UserId clientId
        ) => new Order(
            id,
            orderNumber,
            totalPrice,
            status,
            draft,
            clientId
        );


    }
}