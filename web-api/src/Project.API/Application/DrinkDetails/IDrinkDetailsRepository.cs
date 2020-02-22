using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Application.DrinkDetails
{
    public interface IDrinkDetailsRepository
    {
        Task<IEnumerable<DrinkDetails>> AvailableDrinkDetails(CancellationToken token = default(CancellationToken));
    }
}