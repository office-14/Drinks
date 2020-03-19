namespace Project.API.Ordering.Domain.Clients
{
    public sealed class Client
    {
        private Client() : this(null) { }

        private Client(DeviceId? deviceId) => DeviceId = deviceId;

        public DeviceId? DeviceId { get; }

        public static Client New() => new Client();

        public static Client New(DeviceId deviceId) => new Client(deviceId);
    }
}