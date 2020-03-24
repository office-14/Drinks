import React from 'react'
import { Grid } from '@material-ui/core'

import ChangeLocale from './ChangeLocale'

function Settings() {
  return (
    <Grid container justify="center">
      <Grid item xs={5}>
        <ChangeLocale />
      </Grid>
    </Grid>
  )
}

export default Settings
