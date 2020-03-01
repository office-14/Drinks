using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Ordering.Domain.Drinks
{
    public interface IDrinksRepository
    {
        // TODO: discuss repositories pattern and asynchrony leakage
        // into domain logic. Is it good/bad?
        // TODO: discuss IAsyncEnumerable<Drink> usage
        Task<IEnumerable<Drink>> ListAvailableDrinks(CancellationToken token = default);

        Task<Drink?> DrinkWithId(DrinkId id, CancellationToken token = default);
    }
}