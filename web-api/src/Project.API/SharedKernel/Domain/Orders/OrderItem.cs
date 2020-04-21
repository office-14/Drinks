using System;
using System.Collections.Generic;
using Project.API.Ordering.Domain.Drinks;
using Project.API.SharedKernel.Domain.Core;

namespace Project.API.SharedKernel.Domain.Orders
{
    public sealed class OrderItem
    {
        private readonly List<AddIn> addIns = new List<AddIn>();

        private OrderItem(Drink drink, DrinkSize drinkSize, Quantity count) =>
            (Drink, Size, Count) = (drink, drinkSize, count);

        public Drink Drink { get; }

        public DrinkSize Size { get; }

        public Quantity Count { get; }

        public IReadOnlyCollection<AddIn> AddIns
        {
            get => addIns.AsReadOnly();
        }

        public void AddAddIn(AddIn addIn)
        {
            if (this.addIns.Contains(addIn))
                throw new ArgumentException("Cannot add same add-in multiple times");

            this.addIns.Add(addIn);
        }

        public Roubles TotalPrice()
        {
            var drinkPrice = Size.Price;

            foreach (var addIn in addIns)
            {
                drinkPrice = drinkPrice.Add(addIn.Price);
            }

            drinkPrice = drinkPrice.Times(Count);

            return drinkPrice;
        }

        public static OrderItem New(Drink drink, DrinkSize size, Quantity count)
        {
            return new OrderItem(drink, size, count);
        }
    }
}