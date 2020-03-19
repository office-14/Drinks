using System.Collections.Generic;
using Project.API.Ordering.Domain.Clients;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Infrastructure.Notifications
{
    internal sealed class NotifiableOrders : Dictionary<OrderId, Client>
    {
    }
}