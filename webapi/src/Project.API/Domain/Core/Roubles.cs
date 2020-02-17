using System;

namespace Project.API.Domain.Core
{
    public sealed class Roubles
    {
        public Roubles(int amount)
        {
            if (amount < 0) throw new ArgumentOutOfRangeException(nameof(amount),
                 amount, "Roubles amount cannot be negative");

            this.Amount = amount;
        }

        public int Amount { get; }
    }
}