using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Project.API.WebApi.Endpoints.Shared;

namespace Project.API.WebApi.Endpoints.AddIns
{
    [Route("api/add-ins")]
    public class AddInsController : ControllerBase
    {
        [HttpGet]
        public ResponseWrapper<IEnumerable<AddInItem>> AvailableDrinks()
        {
            return ResponseWrapper<IEnumerable<AddInItem>>.From(new[] {
                new AddInItem {
                    Id = 2,
                    Name = "Milk",
                    Description = "3.5% fresh milk",
                    PhotoUrl = "http://photo-of-milk.url",
                    Price = 10
                }
            });
        }
    }
}