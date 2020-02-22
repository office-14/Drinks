using System;

namespace Project.API.Domain.Drinks
{
    public readonly struct Volume
    {
        private Volume(string value) => Value = value;

        public string Value { get; }

        public static Volume From(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("Volume value cannot be empty");

            return new Volume(value);
        }
    }
}