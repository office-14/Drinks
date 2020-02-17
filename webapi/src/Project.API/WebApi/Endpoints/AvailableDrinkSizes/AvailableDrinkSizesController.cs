using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Domain.Drinks;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.AvailableDrinkSizes
{
    public class AvailableDrinkSizesController : ControllerBase
    {
        private readonly IDrinkSizesRepository drinkSizesRepository;

        public AvailableDrinkSizesController(IDrinkSizesRepository drinkSizesRepository)
        {
            this.drinkSizesRepository = drinkSizesRepository;
        }

        [HttpGet("api/drinks/{id}/sizes")]
        public async Task<ActionResult<ResponseWrapper<IEnumerable<AvailableSize>>>> AvailableSizesOfDrink([FromRoute] int id)
        {
            var availableSizes = await drinkSizesRepository.ListSizesOfDrink(id);

            if (availableSizes == null)
            {
                return NotFound();
            }

            return ResponseWrapper<IEnumerable<AvailableSize>>.From(availableSizes.Select(AvailableSize.From));
        }
    }
}