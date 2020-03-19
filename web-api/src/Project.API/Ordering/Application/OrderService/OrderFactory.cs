using System.Threading.Tasks;
using Project.API.Ordering.Domain.Orders;

namespace Project.API.Ordering.Application.OrderService
{
    public sealed class OrderFactory
    {
        private readonly OrderDraftFactory orderDraftFactory;

        private readonly OrderNumberProvider orderNumberProvider;

        public OrderFactory(
            OrderDraftFactory orderDraftFactory,
            OrderNumberProvider orderNumberProvider
        ) =>
            (this.orderDraftFactory, this.orderNumberProvider) =
            (orderDraftFactory, orderNumberProvider);

        public async Task<Order> CreateOrderFrom(ClientOrder clientOrder)
        {
            var orderDraft = await orderDraftFactory.CreateOrderDraftFrom(clientOrder);

            return Order.New(
                orderNumberProvider.Generate(),
                orderDraft.TotalPrice(),
                orderDraft
            );
        }
    }
}