using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Domain.Drinks
{
    public interface IDrinkSizesRepository
    {
        Task<IEnumerable<DrinkSize>> ListSizesOfDrink(int drinkId, CancellationToken token = default(CancellationToken));
    }
}