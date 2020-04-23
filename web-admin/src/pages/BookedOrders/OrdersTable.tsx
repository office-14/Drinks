import React from 'react'
import MaterialTable from 'material-table'
import DoneOutline from '@material-ui/icons/DoneOutline'
import Refresh from '@material-ui/icons/Refresh'

import { useTranslation } from 'localization'
import OrderDrinkItem from './OrderDrinkItem'
import { BookedOrder, BookedOrderItem } from 'api'

type OrderRow = { index: number } & BookedOrder

function buildTableView(orders: BookedOrder[]): OrderRow[] {
  return orders.map((v, i) => ({ index: i + 1, ...v }))
}

function onRowClick() {}

function renderOrderDetails(items: BookedOrderItem[]) {
  return <>{items.map(renderOrderItem)}</>
}

function renderOrderItem(
  { drink_name, drink_volume, 'add-ins': addIns, count }: BookedOrderItem,
  idx: number
) {
  return (
    <OrderDrinkItem
      key={idx}
      name={drink_name}
      volume={drink_volume}
      addIns={addIns}
      count={count}
    />
  )
}

type OrdersTableProps = {
  isLoading: boolean
  onFinishOrder: (id: number, orderNumber: string) => void
  onRefreshTable: () => void
  data: BookedOrder[]
}

function OrdersTable({
  isLoading,
  data,
  onFinishOrder,
  onRefreshTable,
}: OrdersTableProps) {
  const t = useTranslation()

  const tableView = React.useMemo(() => buildTableView(data), [data])

  return (
    // @ts-ignore
    <MaterialTable
      isLoading={isLoading}
      title={t('bookedOrdersTable.title')}
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
        {
          title: t('bookedOrdersTable.comment'),
          field: 'comment',
          width: 300,
        },
      ]}
      data={tableView}
      actions={[
        {
          icon: () => <DoneOutline />,
          tooltip: t('bookedOrdersTable.finishOrder'),
          onClick: (_, rowData) => {
            const order = rowData as BookedOrder

            onFinishOrder(order.id, order.order_number)
          },
        },
        {
          icon: () => <Refresh />,
          tooltip: t('bookedOrdersTable.refreshData'),
          isFreeAction: true,
          onClick: onRefreshTable,
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

export default OrdersTable
