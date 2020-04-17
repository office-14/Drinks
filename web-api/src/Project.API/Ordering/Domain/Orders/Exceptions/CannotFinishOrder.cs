using Project.API.Ordering.Domain.Exceptions;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Domain.Orders.Exceptions
{
    public class CannotFinishOrder : DrinksDomainException
    {
        private CannotFinishOrder(string message)
            : base(message)
        {
        }

        public static CannotFinishOrder BecauseOrderIsAlreadyFinished(OrderId id)
        {
            return new CannotFinishOrder($"Can't finish order with id='{id.Value}' because it's already finished");
        }
    }
}