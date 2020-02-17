using System;

namespace Project.API.Domain.Core
{
    public class Description
    {
        public Description(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("Description cannot be empty");

            Value = value;
        }

        public string Value { get; }
    }
}