using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Project.API.Domain.Drinks;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.AvailableDrinkSizes
{
    public class AvailableDrinkSizesController : ControllerBase
    {
        private readonly IDrinkSizesRepository drinkSizesRepository;
        private readonly ILogger<AvailableDrinkSizesController> logger;

        public AvailableDrinkSizesController(
            IDrinkSizesRepository drinkSizesRepository,
            ILogger<AvailableDrinkSizesController> logger
        )
        {
            this.drinkSizesRepository = drinkSizesRepository;
            this.logger = logger;
        }

        [HttpGet("api/drinks/{id}/sizes")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<IEnumerable<AvailableSize>>), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<ResponseWrapper<IEnumerable<AvailableSize>>>> AvailableSizesOfDrink([FromRoute] int id)
        {
            var availableSizes = await drinkSizesRepository.ListSizesOfDrink(DrinkId.From(id));

            if (availableSizes == null)
            {
                logger.LogWarning("Available drink sizes for drink id='{}' return null", id);
                return NotFound();
            }

            return ResponseWrapper<IEnumerable<AvailableSize>>.From(availableSizes.Select(AvailableSize.From));
        }
    }
}