import React from 'react'
import { useSnackbar } from 'notistack'

import { bookedOrders, finishOrder, BookedOrder } from 'api'
import { useTranslation } from 'localization'

import OrdersTable from './OrdersTable'

function BookedOrders() {
  const [orders, setOrders] = React.useState<BookedOrder[]>([])
  const [ordersLoading, setOrdersLoading] = React.useState(false)
  const [orderFinishing, setOrderFinishing] = React.useState(false)

  const { enqueueSnackbar: notify } = useSnackbar()
  const t = useTranslation()

  const invalidateBookedOrders = React.useCallback(async () => {
    try {
      const fetchedOrders = await bookedOrders()
      setOrders(fetchedOrders)
    } catch (error) {
      notify(
        t('bookedOrdersTable.errors.cannotUpdateTable', {
          reason: error.message,
        }),
        {
          variant: 'error',
        }
      )
      setOrders([])
    }
  }, [setOrders, notify, t])

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
          t('bookedOrdersTable.errors.cannotFinishOrder', {
            orderNumber,
            reason: error.message,
          }),
          { variant: 'error' }
        )
        setOrderFinishing(false)
        return
      }

      notify(
        t('bookedOrdersTable.orderWasFinished', {
          orderNumber,
        }),
        {
          variant: 'success',
        }
      )
      setOrderFinishing(false)
    },
    [notify, refreshBookedOrders, t]
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
