using MediatR;

namespace Project.API.SharedKernel.Events
{
    // TODO: I'm unsure whether it's good to use 3-rd party dependency
    // in core domain objects (MediatR's INotification interface).
    public abstract class DomainEvent : INotification { }
}