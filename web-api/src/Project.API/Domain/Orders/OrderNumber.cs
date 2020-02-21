using System;

namespace Project.API.Domain.Orders
{
    public sealed class OrderNumber
    {
        public OrderNumber(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("Order number cannot be empty");

            Value = value;
        }

        public string Value { get; }
    }
}