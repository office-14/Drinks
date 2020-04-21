using System;

namespace Project.API.SharedKernel.Domain.Core
{
    public readonly struct Quantity
    {
        private Quantity(int value) => Value = value;

        public int Value { get; }

        public static Quantity From(int value)
        {
            if (value < 1)
            {
                throw new ArgumentOutOfRangeException(
                    nameof(value),
                    value,
                    "Quantity value cannot be less than 1."
                );
            }

            return new Quantity(value);
        }
    }
}