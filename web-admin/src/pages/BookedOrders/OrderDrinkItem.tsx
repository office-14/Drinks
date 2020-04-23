import React from 'react'

type OrderDrinkItemProps = {
  name: string
  volume: string
  addIns: string[]
  count: number
}

function OrderDrinkItem({ name, volume, addIns, count }: OrderDrinkItemProps) {
  let formattedItem = `${count} x ${name} (${volume})`
  if (addIns.length > 0) {
    formattedItem += ` (+ ${addIns.toString()})`
  }
  return <p>{formattedItem}</p>
}

export default OrderDrinkItem
