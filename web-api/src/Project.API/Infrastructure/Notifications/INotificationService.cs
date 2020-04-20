using System.Collections.Generic;
using System.Threading.Tasks;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    public interface INotificationService
    {
        Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, IEnumerable<DeviceToken> tokens);
    }
}