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
                Name.From("Мороженное"),
                Description.From("Аффогато - это шарик ванильного мороженого политый сверху порцией эспрессо. Для приготовления аффогато требуется около двух минут и еще меньше времени, чтобы выпить его."),
                new Uri("https://storage.googleapis.com/images.office-14.com/add-ins/ice-cream.jpg"),
                Roubles.From(30)
            ),
            AddIn.Available(
                AddInId.From(7),
                Name.From("Ваниль"),
                Description.From("Время от времени вы можете побаловать себя экстрактом ванили. Он выведет ваш кофе на совершенно новый уровень (и вы, возможно, никогда не захотите возвращаться!)."),
                new Uri("https://storage.googleapis.com/images.office-14.com/add-ins/vanilla.jpg"),
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