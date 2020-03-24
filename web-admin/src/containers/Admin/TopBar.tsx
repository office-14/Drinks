import React from 'react'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import IconButton from '@material-ui/core/IconButton'
import MenuIcon from '@material-ui/icons/Menu'
import { makeStyles } from '@material-ui/core'

import { useTranslation } from 'localization'

import UserMenu from './UserMenu'

const useStyles = makeStyles(theme => ({
  title: {
    flexGrow: 1
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1
  },
  menuButton: {
    marginRight: 8
  }
}))

interface TopBarProps {
  onMenuButtonClick: React.MouseEventHandler
}

function TopBar({ onMenuButtonClick }: TopBarProps) {
  const classes = useStyles()
  const t = useTranslation()

  return (
    <AppBar position="fixed" className={classes.appBar}>
      <Toolbar variant="regular">
        <IconButton
          color="inherit"
          aria-label="open drawer"
          onClick={onMenuButtonClick}
          edge="start"
          className={classes.menuButton}
        >
          <MenuIcon />
        </IconButton>
        <Typography
          className={classes.title}
          component="h1"
          variant="h6"
          color="inherit"
          noWrap
        >
          {t('topbar.adminTitle')}
        </Typography>
        <UserMenu />
      </Toolbar>
    </AppBar>
  )
}

export default TopBar
