using Project.API.Domain.Exceptions;

namespace Project.API.Application.OrderService.Exceptions
{
    public class CannotCreateOrder : DrinksDomainException
    {
        private CannotCreateOrder(string message)
            : base(message)
        {
        }

        public static CannotCreateOrder BecauseOrderHasNoDrinks()
        {
            return new CannotCreateOrder("Cannot create order without drinks");
        }

        public static CannotCreateOrder BecauseDrinkDoesntExist(int drinkId)
        {
            return new CannotCreateOrder($"Cannot add drink with id='{drinkId}' because drink doesn't exist");
        }

        public static CannotCreateOrder BecauseDrinkOfGivenSizeDoesntExist(int drinkId, int drinkSizeId)
        {
            return new CannotCreateOrder($"Cannot add drink with id='{drinkId}' of size with id='{drinkSizeId}' because the drink size doesn't exist");
        }

        public static CannotCreateOrder BecauseAddInDoesntExist(int addInId)
        {
            return new CannotCreateOrder($"Cannot add add-in with id='{addInId}' because it doesn't exist");
        }
    }
}