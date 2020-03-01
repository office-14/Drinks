/* tslint:disable */
import React from 'react'
import MaterialTable from 'material-table'

async function handleDataUpdate(): Promise<any> {
  const ordersResponse = await fetch('http://localhost:5000/api/orders/booked')
  const orders = await ordersResponse.json()
  return {
    data: orders.payload
  }
}

function BookedOrders() {
  return (
    <MaterialTable
      title="Booked orders"
      columns={[{ title: 'Id', field: 'id' }]}
      data={handleDataUpdate}
      actions={[
        {
          icon: 'save',
          tooltip: 'Save User',
          onClick: (event, rowData: any) =>
            fetch(`http://localhost:5000/api/orders/${rowData.id}/finish`, {
              method: 'POST'
            })
        }
      ]}
      options={{
        paging: false,
        search: false,
        actionsColumnIndex: -1
      }}
    />
  )
}

export default BookedOrders
