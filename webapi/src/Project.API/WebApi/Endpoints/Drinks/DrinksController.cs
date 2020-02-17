using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Domain.Drinks;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.Drinks
{
    public class DrinksController : ControllerBase
    {
        [HttpGet("api/drinks/{id}/sizes")]
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