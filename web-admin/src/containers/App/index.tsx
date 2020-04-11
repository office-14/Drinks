import React from 'react'
import { Switch, Route } from 'react-router-dom'
import CssBaseline from '@material-ui/core/CssBaseline'
import { SnackbarProvider } from 'notistack'

import { SecureRoute, routes } from 'routing'
import Login from 'pages/Login'
import Logout from 'pages/Logout'
import Admin from 'containers/Admin'

function App() {
  return (
    <>
      <CssBaseline />
      <SnackbarProvider maxSnack={1}>
        <Switch>
          <Route path={routes.LOGIN} component={Login} />
          <Route path={routes.LOGOUT} component={Logout} />
          <SecureRoute component={Admin} />
        </Switch>
      </SnackbarProvider>
    </>
  )
}

export default App
