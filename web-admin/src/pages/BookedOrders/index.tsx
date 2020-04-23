import React from 'react'

import OrdersTable from './OrdersTable'
import { bookedOrders, finishOrder, BookedOrder } from 'api'
import { useSnackbar } from 'notistack'

function BookedOrders() {
  const [orders, setOrders] = React.useState<BookedOrder[]>([])
  const [ordersLoading, setOrdersLoading] = React.useState(false)
  const [orderFinishing, setOrderFinishing] = React.useState(false)

  const { enqueueSnackbar: notify } = useSnackbar()

  const invalidateBookedOrders = React.useCallback(async () => {
    try {
      const fetchedOrders = await bookedOrders()
      setOrders(fetchedOrders)
    } catch (error) {
      notify(`Cannot update booked orders table. Reason: ${error.message}.`, {
        variant: 'error',
      })
      setOrders([])
    }
  }, [setOrders, notify])

  const refreshBookedOrders = React.useCallback(async () => {
    setOrdersLoading(true)
    await invalidateBookedOrders()
    setOrdersLoading(false)
  }, [setOrdersLoading, invalidateBookedOrders])

  const handleFinishOrder = React.useCallback(
    async (id: number, orderNumber: string) => {
      setOrderFinishing(true)

      try {
        await finishOrder(id)
        await refreshBookedOrders()
      } catch (error) {
        notify(
          `Cannot finish order ${orderNumber}. Reason: ${error.message}.`,
          { variant: 'error' }
        )
        setOrderFinishing(false)
        return
      }

      notify(`Order ${orderNumber} was finished`, {
        variant: 'success',
      })
      setOrderFinishing(false)
    },
    [notify, refreshBookedOrders]
  )

  React.useEffect(() => {
    invalidateBookedOrders()
    const interval = setInterval(invalidateBookedOrders, 5000)
    return () => clearInterval(interval)
  }, [invalidateBookedOrders])

  return (
    <OrdersTable
      data={orders}
      isLoading={ordersLoading || orderFinishing}
      onFinishOrder={handleFinishOrder}
      onRefreshTable={refreshBookedOrders}
    />
  )
}

export default BookedOrders
