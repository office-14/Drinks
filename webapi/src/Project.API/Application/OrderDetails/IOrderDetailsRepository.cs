using System.Threading;
using System.Threading.Tasks;

namespace Project.API.Application.OrderDetails
{
    public interface IOrderDetailsRepository
    {
        Task<OrderDetails> GetOrderDetailsById(int orderId, CancellationToken token = default(CancellationToken));
    }
}