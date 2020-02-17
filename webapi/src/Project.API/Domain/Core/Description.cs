using System;

namespace Project.API.Domain.Core
{
    public sealed class Description
    {
        private Description(string value)
        {
            Value = value;
        }

        public string Value { get; }

        public static Description From(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("Description cannot be empty");

            return new Description(value);
        }
    }
}