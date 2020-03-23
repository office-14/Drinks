import React from 'react'
import { Switch, Route } from 'react-router-dom'

import { routes } from 'routing'
import Home from 'pages/Home'
import NotFound from 'pages/NotFound'

import AdminPage from './AdminPage'

function Admin() {
  return (
    <AdminPage>
      <Switch>
        <Route exact path={routes.HOME} component={Home} />
        <Route path={routes.BOOKED_ORDERS} component={Home} />
        <Route component={NotFound} />
      </Switch>
    </AdminPage>
  )
}

export default Admin
