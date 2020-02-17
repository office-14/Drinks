using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Project.API.Domain.AddIns;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.AddIns
{
    [Route("api/add-ins")]
    public class AddInsController : ControllerBase
    {
        private readonly IAddInsRepository addInsRepository;

        public AddInsController(IAddInsRepository addInsRepository)
        {
            this.addInsRepository = addInsRepository;
        }

        [HttpGet]
        public async Task<ResponseWrapper<IEnumerable<AddInItem>>> AvailableDrinks()
        {
            var addIns = await addInsRepository.ListAvailableAddIns();

            return ResponseWrapper<IEnumerable<AddInItem>>.From(addIns.Select(AddInItem.From));
        }
    }
}