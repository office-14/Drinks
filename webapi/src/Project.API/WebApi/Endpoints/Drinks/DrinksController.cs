using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.Drinks
{
    [Route("api/drinks")]
    public class DrinksController : ControllerBase
    {
        [HttpGet]
        public ResponseWrapper<IEnumerable<DrinkItem>> AvailableDrinks()
        {
            return ResponseWrapper<IEnumerable<DrinkItem>>.From(new[] {
                new DrinkItem {
                    Id = 1,
                    Name = "Glisse",
                    Description = "Tasty glisse",
                    PhotoUrl = "http://photo.url",
                }
            });
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