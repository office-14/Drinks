const basePath = process.env.REACT_APP_WEB_API || ''

export const bookedOrders = `${basePath}/api/orders/booked`

export const finishOrder = (id: number) => `${basePath}/api/orders/${id}/finish`
