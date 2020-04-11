import React from 'react'
import MaterialTable from 'material-table'
import DoneOutline from '@material-ui/icons/DoneOutline'
import Refresh from '@material-ui/icons/Refresh'
import { useSnackbar } from 'notistack'

import { useAuth } from 'auth'
import { useTranslation } from 'localization'
import { endpoints } from 'api'

function renderOrderDetails(items: any[]) {
  return <>{items.map(renderOrderItem)}</>
}

function renderOrderItem(
  { drink_name, drink_volume, 'add-ins': addIns }: any,
  idx: number
) {
  let formattedItem = `${drink_name} (${drink_volume})`
  if (addIns.length > 0) {
    formattedItem += ` (+ ${addIns.toString()})`
  }
  return <p key={idx}>{formattedItem}</p>
}

function onRowClick() {}

function BookedOrders() {
  const { enqueueSnackbar: notify } = useSnackbar()
  const { user } = useAuth()
  const t = useTranslation()

  const tableRef: any = React.useRef()

  async function handleDataUpdate(): Promise<any> {
    const idToken = await user?.getIdToken()
    const ordersResponse = await fetch(endpoints.bookedOrders, {
      headers: {
        Authorization: `Bearer ${idToken}`,
      },
    })
    const orders = await ordersResponse.json()
    return {
      data: orders.payload.map((o: any, idx: number) => ({
        index: idx + 1,
        ...o,
      })),
    }
  }

  const handleRefresh = React.useCallback(() => {
    tableRef.current && tableRef.current.onQueryChange()
  }, [tableRef])

  const handleFinishOrder = React.useCallback(
    async (event, rowData) => {
      const idToken = await user?.getIdToken()

      let response
      try {
        response = await fetch(endpoints.finishOrder(rowData.id), {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${idToken}`,
          },
        })
      } catch (e) {
        notify(e, { variant: 'error' })
        return
      }

      if (response.status > 299) {
        const message = (await response.json()).title
        notify(`Cannot finish order: ${message}`, { variant: 'error' })
        return
      }

      notify(`Order ${rowData.order_number} was finished`, {
        variant: 'success',
      })

      handleRefresh()
    },
    [handleRefresh, notify, user]
  )

  React.useEffect(() => {
    const interval = setInterval(handleRefresh, 5000)
    return () => clearInterval(interval)
  }, [handleRefresh])

  return (
    //@ts-ignore
    <MaterialTable
      title={t('bookedOrdersTable.title')}
      tableRef={tableRef}
      onRowClick={onRowClick} // Used to enable row highlight
      columns={[
        { title: '#', field: 'index', width: 60 },
        {
          title: t('bookedOrdersTable.orderNumber'),
          field: 'order_number',
          width: 150,
        },
        {
          title: t('bookedOrdersTable.totalPrice'),
          field: 'total_price',
          render: (row) => `${row.total_price} ₽`,
          width: 150,
        },
        {
          title: t('bookedOrdersTable.details'),
          field: 'items',
          render: (row) => renderOrderDetails(row.items),
        },
      ]}
      data={handleDataUpdate}
      actions={[
        {
          icon: () => <DoneOutline />,
          tooltip: t('bookedOrdersTable.finishOrder'),
          onClick: handleFinishOrder,
        },
        {
          icon: () => <Refresh />,
          tooltip: t('bookedOrdersTable.refreshData'),
          isFreeAction: true,
          onClick: handleRefresh,
        },
      ]}
      options={{
        paging: false,
        search: false,
        draggable: false,
        sorting: false,
        actionsColumnIndex: -1,
      }}
      localization={{
        body: {
          emptyDataSourceMessage: t('bookedOrdersTable.noRecordsToDisplay'),
        },
        header: {
          actions: t('bookedOrdersTable.actions'),
        },
      }}
    />
  )
}

export default BookedOrders
