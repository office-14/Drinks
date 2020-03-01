using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Servicing.Application.BookedOrders
{
    public interface IBookedOrdersRepository
    {
        Task<IEnumerable<BookedOrder>> BookedOrders(CancellationToken token = default);
    }
}