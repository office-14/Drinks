namespace Project.API.Ordering.Domain.Users
{
    public sealed class User
    {
        private User(UserId id) => Id = id;

        public UserId Id { get; }

        public static User New(UserId id) => new User(id);
    }
}