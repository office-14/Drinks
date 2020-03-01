using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Project.API.Ordering.Domain.Drinks;
using Project.API.Ordering.Domain.Core;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.Infrastructure.Repositories
{
    public sealed class InMemoryAddInsRepository : IAddInsRepository
    {
        private static readonly IEnumerable<AddIn> AvailableAddIns = new List<AddIn>
        {
            AddIn.Available(
                AddInId.From(4),
                Name.From("Ice Cream"),
                Description.From("An affogato is a scoop of vanilla ice cream with a shot of espresso poured over top. The standard variety takes about two minutes to prepare and even less time to guzzle down."),
                new Uri("https://www.tasteofhome.com/wp-content/uploads/2018/08/shutterstock_413974858.jpg"),
                Roubles.From(30)
            ),
            AddIn.Available(
                AddInId.From(7),
                Name.From("Vanilla"),
                Description.From("Treat yourself to some rich vanilla extract now and then. Adding this to your coffee will take it to a whole new level (and you may never want to go back!)."),
                new Uri("https://www.tasteofhome.com/wp-content/uploads/2018/10/vanilla-extract.jpg"),
                Roubles.From(25)
            )
        };

        public Task<AddIn?> AddInWithId(AddInId id, CancellationToken token = default)
        {
            return Task.FromResult<AddIn?>(AvailableAddIns.FirstOrDefault(a => a.Id.Equals(id)));
        }

        public Task<IEnumerable<AddIn>> ListAvailableAddIns(CancellationToken token = default) =>
            Task.FromResult(AvailableAddIns);
    }
}