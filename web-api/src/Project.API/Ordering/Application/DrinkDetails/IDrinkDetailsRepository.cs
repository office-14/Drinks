using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Ordering.Application.DrinkDetails
{
    public interface IDrinkDetailsRepository
    {
        Task<IEnumerable<DrinkDetails>> AvailableDrinkDetails(CancellationToken token = default);
    }
}