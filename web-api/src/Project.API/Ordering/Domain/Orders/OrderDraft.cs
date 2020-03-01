using System.Collections.Generic;
using System.Linq;
using Project.API.Ordering.Domain.Core;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.Ordering.Domain.Orders
{
    public sealed class OrderDraft
    {
        private readonly List<OrderItem> orderItems = new List<OrderItem>();

        private OrderDraft() { }

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