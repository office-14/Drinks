using System.Threading.Tasks;
using MediatR;
using Project.API.Ordering.Domain.Clients;
using Project.API.Ordering.Domain.Orders;
using Project.API.Ordering.Events;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.OrderService
{
    public sealed class CreateOrderService
    {
        private readonly OrderFactory orderFactory;

        private readonly IOrdersRepository orders;

        // TODO: I'm unsure whether it's good to use 3rd party service
        // expliciltly in application layer. Maybe we need to abstract it?
        private readonly IMediator mediator;

        public CreateOrderService(
            OrderFactory orderFactory,
            IOrdersRepository ordersRepository,
            IMediator mediator
        ) =>
            (this.orderFactory, orders, this.mediator) =
            (orderFactory, ordersRepository, mediator);

        public async Task<OrderId> CreateNewOrder(
            Client client,
            ClientOrder clientOrder
        )
        {
            var order = await orderFactory.CreateOrderFrom(clientOrder);

            var persistedOrder = await orders.Save(order);

            // TODO: Should we use default MediatR publish strategy here?
            // https://github.com/jbogard/MediatR/wiki#publish-strategies
            await mediator.Publish(new OrderIsCreated(persistedOrder.Id, client));

            return persistedOrder.Id;
        }
    }
}