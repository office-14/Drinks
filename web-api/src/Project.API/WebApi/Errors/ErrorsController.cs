using System;
using Microsoft.AspNetCore.Diagnostics;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Hosting;
using Project.API.Ordering.Domain.Exceptions;

namespace Project.API.WebApi.Errors
{
    [ApiExplorerSettings(IgnoreApi = true)]
    public class ErrorsController : ControllerBase
    {
        [Route("/error")]
        public IActionResult ErrorHandler([FromServices] IWebHostEnvironment environment)
        {
            var context = HttpContext.Features.Get<IExceptionHandlerFeature>();

            if (context.Error is ArgumentException)
            {
                return Problem(
                    statusCode: StatusCodes.Status400BadRequest,
                    title: context.Error.Message,
                    detail: environment.IsDevelopment() ? context.Error.StackTrace : null
                );
            }
            else if (context.Error is DrinksDomainException)
            {
                return Problem(
                    statusCode: StatusCodes.Status400BadRequest,
                    title: context.Error.Message,
                    detail: environment.IsDevelopment() ? context.Error.StackTrace : null
                );
            }

            return Problem();
        }
    }
}