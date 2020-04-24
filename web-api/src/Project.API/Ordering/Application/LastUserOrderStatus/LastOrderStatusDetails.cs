using Project.API.Ordering.Domain.Orders;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.LastUserOrderStatus
{
    public sealed class LastOrderStatusDetails
    {
        private LastOrderStatusDetails(OrderId orderId, Status status) =>
            (OrderId, Status) = (orderId, status);

        public OrderId OrderId { get; }

        public Status Status { get; }

        public static LastOrderStatusDetails Current(OrderId orderId, Status status)
            => new LastOrderStatusDetails(orderId, status);
    }
}