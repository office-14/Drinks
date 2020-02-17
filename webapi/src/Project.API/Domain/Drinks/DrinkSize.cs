using Project.API.Domain.Core;

namespace Project.API.Domain.Drinks
{
    public sealed class DrinkSize
    {
        private DrinkSize(
            int id,
            Name name,
            Volume volume,
            Roubles price
        )
        {
            Id = id;
            Name = name;
            Volume = volume;
            Price = price;
        }

        public int Id { get; }

        public Volume Volume { get; }

        public Name Name { get; }

        public Roubles Price { get; }

        public static DrinkSize Available(
            int id,
            Name name,
            Volume volume,
            Roubles price
        ) => new DrinkSize(id, name, volume, price);
    }
}