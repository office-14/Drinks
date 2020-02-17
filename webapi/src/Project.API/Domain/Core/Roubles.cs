using System;

namespace Project.API.Domain.Core
{
    public sealed class Roubles
    {
        private Roubles(int amount)
        {
            this.Amount = amount;
        }

        public int Amount { get; }

        public static Roubles From(int amount)
        {
            if (amount < 0) throw new ArgumentOutOfRangeException(nameof(amount),
                 amount, "Roubles amount cannot be negative");

            return new Roubles(amount);
        }
    }
}