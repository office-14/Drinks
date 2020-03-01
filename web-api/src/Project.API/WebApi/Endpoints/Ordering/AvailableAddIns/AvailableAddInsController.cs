using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Project.API.Ordering.Domain.Drinks;
using Project.API.WebApi.Endpoints.Shared;
using Project.API.WebApi.Swagger;

namespace Project.API.WebApi.Endpoints.Ordering.AvailableAddIns
{
    [ApiExplorerSettings(GroupName = AvailableDocuments.Ordering)]
    public class AvailableAddInsController : ControllerBase
    {
        private readonly IAddInsRepository addInsRepository;

        public AvailableAddInsController(IAddInsRepository addInsRepository)
        {
            this.addInsRepository = addInsRepository;
        }

        [HttpGet("api/add-ins")]
        [Produces(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(ResponseWrapper<IEnumerable<AvailableAddIn>>), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ProblemDetails), StatusCodes.Status500InternalServerError)]
        public async Task<ResponseWrapper<IEnumerable<AvailableAddIn>>> AvailableAddIns()
        {
            var addIns = await addInsRepository.ListAvailableAddIns();

            return ResponseWrapper<IEnumerable<AvailableAddIn>>.From(addIns.Select(AvailableAddIn.From));
        }
    }
}