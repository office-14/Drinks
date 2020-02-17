using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Domain.Orders
{
    public interface IOrderDetailsRepository
    {
        Task<OrderDetails> GetOrderDetailsById(int orderId, CancellationToken token = default(CancellationToken));
    }
}