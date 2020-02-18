using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Domain.Drinks
{
    public interface IDrinkSizesRepository
    {
        Task<IEnumerable<DrinkSize>> ListSizesOfDrink(int drinkId, CancellationToken token = default(CancellationToken));

        Task<DrinkSize> SizeOfDrink(int drinkId, int drinkSizeId, CancellationToken token = default(CancellationToken));
    }
}