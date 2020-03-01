using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Application.DrinkDetails;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Ordering.AvailableDrinks
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Ordering)]
    public class AvailableDrinksController : ControllerBase
    {
        private readonly IDrinkDetailsRepository drinksRepository;

        public AvailableDrinksController(IDrinkDetailsRepository drinksRepository)
        {
            this.drinksRepository = drinksRepository;
        }

        [HttpGet("api/drinks")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<IEnumerable<AvailableDrink>>), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<ResponseWrapper<IEnumerable<AvailableDrink>>> AvailableDrinks()
        {
            var drinks = await drinksRepository.AvailableDrinkDetails();

            return ResponseWrapper<IEnumerable<AvailableDrink>>.From(drinks.Select(AvailableDrink.From));
        }
    }
}