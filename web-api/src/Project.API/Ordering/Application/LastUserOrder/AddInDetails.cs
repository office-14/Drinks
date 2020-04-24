using Project.API.Ordering.Domain.Drinks;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.Ordering.Application.LastUserOrder
{
    public sealed class AddInDetails
    {
        private AddInDetails(AddInId addInId, Name name) =>
            (AddInId, Name) = (addInId, name);

        public AddInId AddInId { get; }

        public Name Name { get; }

        public static AddInDetails Ordered(
            AddInId addInId,
            Name name
        ) => new AddInDetails(
            addInId,
            name
        );
    }
}