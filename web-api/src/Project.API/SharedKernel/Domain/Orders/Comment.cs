namespace Project.API.SharedKernel.Domain.Orders
{
    public readonly struct Comment
    {
        private Comment(string? value) => Value = value;

        public string? Value { get; }

        public static Comment From(string? value) => new Comment(value);
    }
}