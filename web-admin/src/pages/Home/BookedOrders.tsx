import React from 'react'
import MaterialTable from 'material-table'
import DoneOutline from '@material-ui/icons/DoneOutline'
import Refresh from '@material-ui/icons/Refresh'
import { useSnackbar } from 'notistack'

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

async function handleDataUpdate(): Promise<any> {
  const ordersResponse = await fetch('http://localhost:5000/api/orders/booked')
  const orders = await ordersResponse.json()
  return {
    data: orders.payload.map((o: any, idx: number) => ({
      index: idx + 1,
      ...o
    }))
  }
}

function onRowClick() {}

function BookedOrders() {
  const { enqueueSnackbar: notify } = useSnackbar()

  const tableRef: any = React.useRef()

  const handleRefresh = React.useCallback(() => {
    tableRef.current && tableRef.current.onQueryChange()
  }, [tableRef])

  const handleFinishOrder = React.useCallback(
    async (event, rowData) => {
      let response
      try {
        response = await fetch(
          `http://localhost:5000/api/orders/${rowData.id}/finish`,
          {
            method: 'POST'
          }
        )
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
        variant: 'success'
      })

      handleRefresh()
    },
    [handleRefresh, notify]
  )

  React.useEffect(() => {
    const interval = setInterval(handleRefresh, 5000)
    return () => clearInterval(interval)
  }, [handleRefresh])

  return (
    //@ts-ignore
    <MaterialTable
      title="Booked orders"
      tableRef={tableRef}
      onRowClick={onRowClick} // Used to enable row highlight
      columns={[
        { title: '#', field: 'index', width: 60 },
        { title: 'Order number', field: 'order_number', width: 150 },
        {
          title: 'Total price',
          field: 'total_price',
          render: row => `${row.total_price} ₽`,
          width: 150
        },
        {
          title: 'Details',
          field: 'items',
          render: row => renderOrderDetails(row.items)
        }
      ]}
      data={handleDataUpdate}
      actions={[
        {
          icon: () => <DoneOutline />,
          tooltip: 'Finish order',
          onClick: handleFinishOrder
        },
        {
          icon: () => <Refresh />,
          tooltip: 'Refresh Data',
          isFreeAction: true,
          onClick: handleRefresh
        }
      ]}
      options={{
        paging: false,
        search: false,
        draggable: false,
        sorting: false,
        actionsColumnIndex: -1
      }}
    />
  )
}

export default BookedOrders
