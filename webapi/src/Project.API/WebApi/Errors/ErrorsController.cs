using System;
using Microsoft.AspNetCore.Diagnostics;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Hosting;
using Project.API.Domain.Exceptions;

namespace Project.API.WebApi.Errors
{
    [ApiExplorerSettings(IgnoreApi = true)]
    public class ErrorsController : ControllerBase
    {
        [Route("/error-dev")]
        public IActionResult ErrorLocalDevelopment([FromServices] IWebHostEnvironment webHostEnvironment)
        {
            if (!webHostEnvironment.IsDevelopment())
            {
                throw new InvalidOperationException(
                    "This shouldn't be invoked in non-development environments.");
            }

            var context = HttpContext.Features.Get<IExceptionHandlerFeature>();

            if (context.Error is ArgumentException)
            {
                return Problem(
                    statusCode: StatusCodes.Status400BadRequest,
                    title: context.Error.Message,
                    detail: context.Error.StackTrace
                );
            }
            else if (context.Error is DrinksDomainException)
            {
                return Problem(
                    statusCode: StatusCodes.Status400BadRequest,
                    title: context.Error.Message,
                    detail: context.Error.StackTrace
                );
            }

            return Problem();
        }
    }
}