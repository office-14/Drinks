using Project.API.Domain.AddIns;
using Project.API.Domain.Drinks;
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

        public static CannotCreateOrder BecauseDrinkDoesntExist(DrinkId drinkId)
        {
            return new CannotCreateOrder($"Cannot add drink with id='{drinkId.Value}' because drink doesn't exist");
        }

        public static CannotCreateOrder BecauseDrinkOfGivenSizeDoesntExist(DrinkId drinkId, DrinkSizeId drinkSizeId)
        {
            return new CannotCreateOrder($"Cannot add drink with id='{drinkId.Value}' of size with id='{drinkSizeId.Value}' because the drink size doesn't exist");
        }

        public static CannotCreateOrder BecauseAddInDoesntExist(AddInId addInId)
        {
            return new CannotCreateOrder($"Cannot add add-in with id='{addInId.Value}' because it doesn't exist");
        }
    }
}