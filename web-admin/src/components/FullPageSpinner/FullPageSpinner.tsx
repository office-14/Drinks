import React from 'react'
import Grid from '@material-ui/core/Grid'
import CircularProgress from '@material-ui/core/CircularProgress'
import { makeStyles } from '@material-ui/core'

const useStyles = makeStyles(theme => ({
  container: {
    height: `calc(100vh - ${theme.spacing(2)}px)`
  }
}))

function FullPageSpinner() {
  const classes = useStyles()

  return (
    <Grid
      className={classes.container}
      container
      justify="center"
      alignItems="center"
    >
      <CircularProgress variant="indeterminate" size={100} thickness={1} />
    </Grid>
  )
}

export default FullPageSpinner
