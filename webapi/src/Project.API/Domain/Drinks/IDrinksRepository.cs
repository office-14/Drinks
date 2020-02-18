using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Domain.Drinks
{
    public interface IDrinksRepository
    {
        // TODO: discuss repositories pattern and asynchrony leakage
        // into domain logic. Is it good/bad?
        // TODO: discuss IAsyncEnumerable<Drink> usage
        Task<IEnumerable<Drink>> ListAvailableDrinks(CancellationToken token = default(CancellationToken));

        Task<Drink> DrinkWithId(int id, CancellationToken token = default(CancellationToken));
    }
}