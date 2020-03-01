namespace Project.API.WebApi.Endpoints.Servicing.BookedOrders
{
    public class BookedOrder
    {
        public int Id { get; set; }

        public static BookedOrder From(API.Servicing.Application.BookedOrders.BookedOrder bookedOrder)
            => new BookedOrder
            {
                Id = bookedOrder.Id.Value
            };
    }
}