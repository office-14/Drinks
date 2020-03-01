using System;

namespace Project.API.Ordering.Domain.Exceptions
{
    public class DrinksDomainException : Exception
    {
        public DrinksDomainException()
        {
        }

        public DrinksDomainException(string message)
            : base(message)
        {
        }

        public DrinksDomainException(string message, Exception inner)
            : base(message, inner)
        {
        }
    }
}