using System.Threading;
using System.Threading.Tasks;
using Project.API.Ordering.Domain.Users;

namespace Project.API.Ordering.Application.LastUserOrder
{
    public interface ILastUserOrderProvider
    {
        Task<LastOrderDetails?> LastUserOrder(User user, CancellationToken token = default);

        Task<bool> UserHasUnfinishedOrder(User user, CancellationToken token = default);
    }
}