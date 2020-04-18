using System.Collections.Generic;
using System.Threading.Tasks;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal interface INotificationService
    {
        Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, IEnumerable<DeviceToken> tokens);
    }
}