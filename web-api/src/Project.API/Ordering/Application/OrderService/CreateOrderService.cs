using System.Threading.Tasks;
using MediatR;
using Project.API.Ordering.Application.LastUserOrder;
using Project.API.Ordering.Application.OrderService.Exceptions;
using Project.API.Ordering.Domain.Orders;
using Project.API.Ordering.Domain.Users;
using Project.API.Ordering.Events;
using Project.API.SharedKernel.Domain.Orders;

namespace Project.API.Ordering.Application.OrderService
{
    public sealed class CreateOrderService
    {
        private readonly OrderFactory orderFactory;

        private readonly IOrdersRepository orders;

        private readonly ILastUserOrderProvider lastOrderProvider;


        // TODO: I'm unsure whether it's good to use 3rd party service
        // expliciltly in application layer. Maybe we need to abstract it?
        private readonly IMediator mediator;

        public CreateOrderService(
            OrderFactory orderFactory,
            IOrdersRepository ordersRepository,
            ILastUserOrderProvider lastOrderProvider,
            IMediator mediator
        )
        {
            (this.orderFactory, orders, this.mediator) =
            (orderFactory, ordersRepository, mediator);
            this.lastOrderProvider = lastOrderProvider;
        }

        public async Task<OrderId> CreateNewOrder(
            User user,
            ClientOrder clientOrder
        )
        {
            if (await lastOrderProvider.UserHasUnfinishedOrder(user))
            {
                throw CannotCreateOrder.BecauseUserAlreadyHasUnfinishedOrder();
            }

            var order = await orderFactory.CreateOrderFrom(user, clientOrder);

            var persistedOrder = await orders.Save(order);

            // TODO: Should we use default MediatR publish strategy here?
            // https://github.com/jbogard/MediatR/wiki#publish-strategies
            await mediator.Publish(new OrderIsCreated(persistedOrder.Id, user.Id));

            return persistedOrder.Id;
        }
    }
}