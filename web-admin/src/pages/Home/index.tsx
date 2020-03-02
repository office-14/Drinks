import React from 'react'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import Container from '@material-ui/core/Container'
import { makeStyles } from '@material-ui/core/styles'

import BookedOrders from './BookedOrders'

const useStyles = makeStyles(theme => ({
  container: {
    paddingTop: theme.spacing(4)
  }
}))

function Home() {
  const classes = useStyles()

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
        <Container maxWidth="lg" className={classes.container}>
          <BookedOrders />
        </Container>
      </main>
    </>
  )
}

export default Home
