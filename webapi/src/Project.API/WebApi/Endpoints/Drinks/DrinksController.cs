using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Domain.Drinks;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.Drinks
{
    [Route("api/drinks")]
    public class DrinksController : ControllerBase
    {
        private readonly IDrinksRepository drinksRepository;

        public DrinksController(IDrinksRepository drinksRepository)
        {
            this.drinksRepository = drinksRepository;
        }

        [HttpGet]
        public async Task<ResponseWrapper<IEnumerable<DrinkItem>>> AvailableDrinks()
        {
            var drinks = await drinksRepository.ListAvailableDrinks();

            return ResponseWrapper<IEnumerable<DrinkItem>>.From(drinks.Select(DrinkItem.From));
        }

        [HttpGet("{id}/sizes")]
        public ResponseWrapper<IEnumerable<DrinkSizeItem>> AvailableSizesOfDrink([FromRoute] int id)
        {
            return ResponseWrapper<IEnumerable<DrinkSizeItem>>.From(new[] {
                new DrinkSizeItem {
                    Id = 1,
                    Volume = "150 ml",
                    Name = "Small",
                    Price = 20
                }
            });
        }
    }
}