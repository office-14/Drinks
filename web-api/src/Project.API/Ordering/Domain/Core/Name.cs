using System;

namespace Project.API.Ordering.Domain.Core
{
    public readonly struct Name
    {
        private Name(string value) => Value = value;

        public string Value { get; }

        public static Name From(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("Name cannot be empty");

            return new Name(value);
        }
    }
}