import React from 'react'
import { useHistory } from 'react-router-dom'
import { makeStyles } from '@material-ui/core'
import IconButton from '@material-ui/core/IconButton'
import Menu from '@material-ui/core/Menu'
import MenuItem from '@material-ui/core/MenuItem'
import Tooltip from '@material-ui/core/Tooltip'
import Avatar from '@material-ui/core/Avatar'

import { routes } from 'routing'
import { useAuth } from 'auth'
import { useTranslation } from 'localization'

const useStyles = makeStyles(theme => ({
  smallAvatar: {
    width: theme.spacing(5),
    height: theme.spacing(5)
  }
}))

function UserMenu() {
  const history = useHistory()
  const { user } = useAuth()
  const classes = useStyles()
  const t = useTranslation()

  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null)

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const handleClose = () => {
    setAnchorEl(null)
  }

  const handleLogout = () => {
    history.push(routes.LOGOUT)
  }

  return (
    <div>
      <Tooltip title={user?.displayName}>
        <IconButton
          aria-label="account of current user"
          aria-controls="menu-appbar"
          aria-haspopup="true"
          onClick={handleMenu}
          color="inherit"
        >
          <Avatar src={user?.photoURL || ''} className={classes.smallAvatar} />
        </IconButton>
      </Tooltip>
      <Menu
        id="menu-appbar"
        anchorEl={anchorEl}
        getContentAnchorEl={null}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right'
        }}
        keepMounted
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right'
        }}
        open={!!anchorEl}
        onClose={handleClose}
      >
        <MenuItem onClick={handleLogout}>{t('userMenu.logout')}</MenuItem>
      </Menu>
    </div>
  )
}

export default UserMenu
