using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Domain.Drinks;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.AvailableDrinks
{
    public class AvailableDrinksController : ControllerBase
    {
        private readonly IDrinksRepository drinksRepository;

        public AvailableDrinksController(IDrinksRepository drinksRepository)
        {
            this.drinksRepository = drinksRepository;
        }

        [HttpGet("api/drinks")]
        public async Task<ResponseWrapper<IEnumerable<AvailableDrink>>> AvailableDrinks()
        {
            var drinks = await drinksRepository.ListAvailableDrinks();

            return ResponseWrapper<IEnumerable<AvailableDrink>>.From(drinks.Select(AvailableDrink.From));
        }
    }
}