using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Ordering.Domain.Drinks
{
    public interface IAddInsRepository
    {
        Task<IEnumerable<AddIn>> ListAvailableAddIns(CancellationToken token = default);

        Task<AddIn?> AddInWithId(AddInId id, CancellationToken token = default);
    }
}