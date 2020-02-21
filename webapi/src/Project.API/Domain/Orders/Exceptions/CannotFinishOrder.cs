using Project.API.Domain.Exceptions;

namespace Project.API.Domain.Orders.Exceptions
{
    public class CannotFinishOrder : DrinksDomainException
    {
        private CannotFinishOrder(string message)
            : base(message)
        {
        }

        public static CannotFinishOrder becauseOrderIsAlreadyFinished(OrderId id)
        {
            return new CannotFinishOrder($"Can't finish order with id='{id.Value}' because it's already finished");
        }
    }
}