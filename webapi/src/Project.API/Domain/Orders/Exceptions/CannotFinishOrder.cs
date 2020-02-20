using Project.API.Domain.Exceptions;

namespace Project.API.Domain.Orders.Exceptions
{
    public class CannotFinishOrder : DrinksDomainException
    {
        private CannotFinishOrder(string message)
            : base(message)
        {
        }

        public static CannotFinishOrder becauseOrderIsAlreadyFinished(int orderId)
        {
            return new CannotFinishOrder($"Can't finish order with id='{orderId}' because it's already finished");
        }
    }
}