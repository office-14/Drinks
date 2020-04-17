using System.Collections.Generic;
using Project.API.Ordering.Domain.Users;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Infrastructure.Repositories.LastOrders
{
    internal sealed class LastUserOrders : Dictionary<UserId, OrderId>
    {
    }
}