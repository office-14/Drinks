using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Application.DrinkDetails;
using Project.API.Domain.Drinks;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.AvailableDrinks
{
    public class AvailableDrinksController : ControllerBase
    {
        private readonly IDrinkDetailsRepository drinksRepository;

        public AvailableDrinksController(IDrinkDetailsRepository drinksRepository)
        {
            this.drinksRepository = drinksRepository;
        }

        [HttpGet("api/drinks")]
        [Produces(MediaTypeNames.Application.Json)]
        public async Task<ResponseWrapper<IEnumerable<AvailableDrink>>> AvailableDrinks()
        {
            var drinks = await drinksRepository.AvailableDrinkDetails();

            return ResponseWrapper<IEnumerable<AvailableDrink>>.From(drinks.Select(AvailableDrink.From));
        }
    }
}