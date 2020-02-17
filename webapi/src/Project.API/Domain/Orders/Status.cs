namespace Project.API.Domain.Orders
{
    public sealed class Status
    {
        public static readonly Status Ready = new Status("READY", "Ready");
        public static readonly Status Cooking = new Status("COOKING", "Cooking");

        private Status(string code, string name)
        {
            Code = code;
            Name = name;
        }

        public string Code { get; }

        public string Name { get; }
    }
}