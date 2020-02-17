using System;

namespace Project.API.Domain.Core
{
    public sealed class Name
    {
        public Name(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("Name cannot be empty");

            Value = value;
        }

        public string Value { get; }
    }
}