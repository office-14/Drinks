using System.Threading;
using System.Threading.Tasks;
using Project.API.Domain.Orders;

namespace Project.API.Application.OrderDetails
{
    public interface IOrderDetailsRepository
    {
        Task<OrderDetails?> OrderDetailsWithId(OrderId id, CancellationToken token = default(CancellationToken));
    }
}