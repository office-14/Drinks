using System.Threading;
using System.Threading.Tasks;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.OrderDetails
{
    public interface IOrderDetailsRepository
    {
        Task<OrderDetails?> OrderDetailsWithId(OrderId id, CancellationToken token = default);
    }
}