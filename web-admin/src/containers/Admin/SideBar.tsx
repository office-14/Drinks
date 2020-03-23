import React from 'react'
import Drawer from '@material-ui/core/Drawer'
import List from '@material-ui/core/List'
import InboxIcon from '@material-ui/icons/MoveToInbox'
import { makeStyles } from '@material-ui/core'
import clsx from 'clsx'

import ListItemLink from 'components/ListItemLink'
import { routes } from 'routing'

const drawerWidth = 240

const useStyles = makeStyles(theme => ({
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap'
  },
  drawerPaper: {
    width: drawerWidth
  },
  drawerOpen: {
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen
    })
  },
  drawerClose: {
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen
    }),
    overflowX: 'hidden',
    width: theme.spacing(7) + 1
  },
  toolbar: theme.mixins.toolbar
}))

interface SideBarProps {
  isOpen: boolean
}

function SideBar({ isOpen }: SideBarProps) {
  const classes = useStyles()

  return (
    <Drawer
      variant="permanent"
      className={clsx(classes.drawer, {
        [classes.drawerOpen]: isOpen,
        [classes.drawerClose]: !isOpen
      })}
      classes={{
        paper: clsx(classes.drawerPaper, {
          [classes.drawerOpen]: isOpen,
          [classes.drawerClose]: !isOpen
        })
      }}
    >
      <div className={classes.toolbar} />
      <List>
        <ListItemLink
          to={routes.BOOKED_ORDERS}
          primary="Booked orders"
          icon={<InboxIcon />}
        />
      </List>
    </Drawer>
  )
}

export default SideBar
