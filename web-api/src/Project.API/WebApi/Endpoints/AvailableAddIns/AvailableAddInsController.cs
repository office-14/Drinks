using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Domain.AddIns;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.AvailableAddIns
{
    public class AvailableAddInsController : ControllerBase
    {
        private readonly IAddInsRepository addInsRepository;

        public AvailableAddInsController(IAddInsRepository addInsRepository)
        {
            this.addInsRepository = addInsRepository;
        }

        [HttpGet("api/add-ins")]
        [Produces(MediaTypeNames.Application.Json)]
        public async Task<ResponseWrapper<IEnumerable<AvailableAddIn>>> AvailableAddIns()
        {
            var addIns = await addInsRepository.ListAvailableAddIns();

            return ResponseWrapper<IEnumerable<AvailableAddIn>>.From(addIns.Select(AvailableAddIn.From));
        }
    }
}