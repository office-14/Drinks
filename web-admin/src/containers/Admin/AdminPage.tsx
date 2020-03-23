import React from 'react'
import { makeStyles } from '@material-ui/core'

import TopBar from './TopBar'
import Page from './Page'
import SideBar from './SideBar'

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex'
  }
}))

const AdminPage: React.FunctionComponent = ({ children }) => {
  const classes = useStyles()
  const [sideBarOpen, setSideBarOpen] = React.useState(false)

  const handleSideBarClick = () => setSideBarOpen(prev => !prev)

  return (
    <div className={classes.root}>
      <TopBar onMenuButtonClick={handleSideBarClick} />
      <SideBar isOpen={sideBarOpen} />
      <Page>{children}</Page>
    </div>
  )
}

export default AdminPage
