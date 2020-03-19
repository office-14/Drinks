using System.Threading.Tasks;
using Project.API.Ordering.Domain.Clients;
using Project.API.Servicing.Events;

namespace Project.API.Infrastructure.Notifications
{
    internal interface INotificationService
    {
        Task NotifyClientWhenOrderIsFinished(OrderIsFinished @event, Client client);
    }
}