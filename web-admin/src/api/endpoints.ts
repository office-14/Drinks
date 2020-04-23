import fetch from 'infrastructure/fetch'

const basePath = process.env.REACT_APP_WEB_API || ''

export type BookedOrderItem = {
  drink_name: string
  drink_volume: string
  'add-ins': string[]
  count: number
}

export type BookedOrder = {
  id: number
  order_number: string
  total_price: number
  comment: string | null
  items: BookedOrderItem[]
}

export async function bookedOrders(): Promise<BookedOrder[]> {
  const response = await fetch(`${basePath}/api/orders/booked`, {
    method: 'GET',
  })
  if (response.ok) {
    const responseJson = await response.json()
    return responseJson.payload
  }
  throw Error(response.statusText)
}

export async function finishOrder(orderId: number): Promise<void> {
  const response = await fetch(`${basePath}/api/orders/${orderId}/finish`, {
    method: 'POST',
  })
  if (!response.ok) {
    throw Error(response.statusText)
  }
}
