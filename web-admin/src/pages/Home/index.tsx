import React from 'react'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'

import BookedOrders from './BookedOrders'

function Home() {
  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography component="h1" variant="h6" color="inherit" noWrap>
            Dashboard - Orders
          </Typography>
        </Toolbar>
      </AppBar>
      <main>
        <BookedOrders />
      </main>
    </>
  )
}

export default Home
