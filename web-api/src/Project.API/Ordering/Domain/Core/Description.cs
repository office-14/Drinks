using System;

namespace Project.API.Ordering.Domain.Core
{
    public readonly struct Description
    {
        private Description(string value) => Value = value;

        public string Value { get; }

        public static Description From(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("Description cannot be empty");

            return new Description(value);
        }
    }
}