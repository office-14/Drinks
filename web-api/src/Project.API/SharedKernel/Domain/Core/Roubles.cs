using System;

namespace Project.API.SharedKernel.Domain.Core
{
    public readonly struct Roubles
    {
        public static readonly Roubles Zero = From(0);

        private Roubles(int amount) => Amount = amount;

        public int Amount { get; }

        public Roubles Add(Roubles other) => From(Amount + other.Amount);

        public Roubles Times(Quantity count) => From(Amount * count.Value);

        public static Roubles From(int amount)
        {
            if (amount < 0) throw new ArgumentOutOfRangeException(nameof(amount),
                 amount, "Roubles amount cannot be negative");

            return new Roubles(amount);
        }
    }
}