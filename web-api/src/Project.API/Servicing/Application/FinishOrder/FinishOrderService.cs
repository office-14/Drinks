using System.Threading.Tasks;
using MediatR;
using Project.API.Ordering.Domain.Orders;
using Project.API.Servicing.Events;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Servicing.Application.FinishOrder
{
    public sealed class FinishOrderService
    {
        private readonly IOrdersRepository ordersRepository;

        private readonly IMediator mediator;

        public FinishOrderService(
            IOrdersRepository ordersRepository,
            IMediator mediator
        ) =>
            (this.ordersRepository, this.mediator) =
            (ordersRepository, mediator);

        public async Task<bool> FinishOrderWithId(OrderId orderId)
        {
            var order = await ordersRepository.OrderWithId(orderId);

            if (order == null) return false;

            order.Finish();
            await ordersRepository.Save(order);

            await mediator.Publish(
                new OrderIsFinished(order.Id, order.OrderNumber, order.ClientId)
            );

            return true;
        }
    }
}