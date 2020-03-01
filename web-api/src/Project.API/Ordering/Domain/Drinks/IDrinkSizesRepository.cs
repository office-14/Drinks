using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Ordering.Domain.Drinks
{
    public interface IDrinkSizesRepository
    {
        Task<IEnumerable<DrinkSize>?> ListSizesOfDrink(DrinkId id, CancellationToken token = default);

        Task<DrinkSize?> SizeOfDrink(DrinkId drinkId, DrinkSizeId drinkSizeId, CancellationToken token = default);
    }
}