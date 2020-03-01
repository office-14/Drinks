using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Servicing.Application.BookedOrders;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Servicing.BookedOrders
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Servicing)]
    public class BookedOrdersController : ControllerBase
    {
        private readonly IBookedOrdersRepository bookedOrdersRepository;

        public BookedOrdersController(IBookedOrdersRepository bookedOrdersRepository)
        {
            this.bookedOrdersRepository = bookedOrdersRepository;
        }

        [HttpGet("api/orders/booked")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<IEnumerable<BookedOrder>>), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<ResponseWrapper<IEnumerable<BookedOrder>>> BookedOrders()
        {
            var bookedOrders = await bookedOrdersRepository.BookedOrders();

            return ResponseWrapper<IEnumerable<BookedOrder>>.From(bookedOrders.Select(BookedOrder.From));
        }
    }
}