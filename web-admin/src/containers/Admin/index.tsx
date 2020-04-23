import React from 'react'
import { Switch, Route } from 'react-router-dom'

import { routes } from 'routing'
import BookedOrders from 'pages/BookedOrders'
import Settings from 'pages/Settings'
import NotFound from 'pages/NotFound'

import AdminPage from './AdminPage'

function Admin() {
  return (
    <AdminPage>
      <Switch>
        <Route exact path={routes.HOME} component={BookedOrders} />
        <Route path={routes.BOOKED_ORDERS} component={BookedOrders} />
        <Route path={routes.SETTINGS} component={Settings} />
        <Route component={NotFound} />
      </Switch>
    </AdminPage>
  )
}

export default Admin
