using System.Threading;
using System.Threading.Tasks;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Domain.Orders
{
    public interface IOrdersRepository
    {
        Task<Order> Save(Order order, CancellationToken token = default);

        Task<Order?> OrderWithId(OrderId id, CancellationToken token = default);
    }
}