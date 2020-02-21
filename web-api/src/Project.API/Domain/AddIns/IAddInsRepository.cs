using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Domain.AddIns
{
    public interface IAddInsRepository
    {
        Task<IEnumerable<AddIn>> ListAvailableAddIns(CancellationToken token = default(CancellationToken));

        Task<AddIn?> AddInWithId(AddInId id, CancellationToken token = default(CancellationToken));
    }
}