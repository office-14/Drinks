import React from 'react'
import { Switch, Route } from 'react-router-dom'
import CssBaseline from '@material-ui/core/CssBaseline'
import { SnackbarProvider } from 'notistack'

import Home from 'pages/Home'
import NotFound from 'pages/NotFound'

function App() {
  return (
    <SnackbarProvider maxSnack={1}>
      <CssBaseline />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/" component={NotFound} />
      </Switch>
    </SnackbarProvider>
  )
}

export default App
