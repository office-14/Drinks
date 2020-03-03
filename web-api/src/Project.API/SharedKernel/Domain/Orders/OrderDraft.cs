using System.Collections.Generic;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.SharedKernel.Domain.Orders
{
    public sealed class OrderDraft
    {
        private readonly List<OrderItem> orderItems = new List<OrderItem>();

        private OrderDraft() { }

        public IReadOnlyCollection<OrderItem> Items
        {
            get => orderItems.AsReadOnly();
        }

        public void AddOrderItem(OrderItem orderItem)
        {
            orderItems.Add(orderItem);
        }

        public Roubles TotalPrice()
        {
            var price = Roubles.Zero;

            foreach (var orderItem in orderItems)
            {
                price = price.Add(orderItem.TotalPrice());
            }

            return price;
        }

        public static OrderDraft New()
        {
            return new OrderDraft();
        }
    }
}