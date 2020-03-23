import React from 'react'
import { makeStyles } from '@material-ui/core'

const useStyles = makeStyles(theme => ({
  content: {
    flexGrow: 1,
    padding: theme.spacing(3)
  },
  toolbar: theme.mixins.toolbar
}))

const Page: React.FunctionComponent = ({ children }) => {
  const classes = useStyles()

  return (
    <main className={classes.content}>
      <div className={classes.toolbar} />
      {children}
    </main>
  )
}

export default Page
