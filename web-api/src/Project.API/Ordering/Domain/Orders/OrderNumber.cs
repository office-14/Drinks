using System;

namespace Project.API.Ordering.Domain.Orders
{
    public readonly struct OrderNumber
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