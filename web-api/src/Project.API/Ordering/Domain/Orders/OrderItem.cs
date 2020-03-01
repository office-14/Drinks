using System;
using System.Collections.Generic;
using Project.API.Ordering.Domain.Drinks;
using Project.API.Ordering.Domain.Core;

namespace Project.API.Ordering.Domain.Orders
{
    public sealed class OrderItem
    {
        private readonly Drink drink;

        private readonly DrinkSize drinkSize;

        private readonly List<AddIn> addIns = new List<AddIn>();

        private OrderItem(Drink drink, DrinkSize drinkSize)
        {
            this.drink = drink;
            this.drinkSize = drinkSize;
        }

        public void AddAddIn(AddIn addIn)
        {
            if (this.addIns.Contains(addIn))
                throw new ArgumentException("Cannot add same add-in multiple times");

            this.addIns.Add(addIn);
        }

        public Roubles TotalPrice()
        {
            var drinkPrice = drinkSize.Price;

            foreach (var addIn in addIns)
            {
                drinkPrice = drinkPrice.Add(addIn.Price);
            }

            return drinkPrice;
        }

        public static OrderItem New(Drink drink, DrinkSize size)
        {
            return new OrderItem(drink, size);
        }
    }
}