using System.Threading;
using System.Threading.Tasks;
using Project.API.Ordering.Domain.Users;

namespace Project.API.Ordering.Application.LastUserOrderStatus
{
    public interface ILastUserOrderStatusProvider
    {
        Task<LastOrderStatusDetails?> StatusOfLastOrder(User user, CancellationToken token = default);
    }
}