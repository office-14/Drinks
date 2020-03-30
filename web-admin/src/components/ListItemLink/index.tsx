// Taken from here: https://material-ui.com/guides/composition/#list
import React from 'react'
import {
  NavLink as RouterLink,
  NavLinkProps as RouterLinkProps
} from 'react-router-dom'
import { Omit } from '@material-ui/types'
import {
  ListItem,
  ListItemIcon,
  ListItemText,
  makeStyles
} from '@material-ui/core'

const useStyles = makeStyles(theme => ({
  link: {
    '&.active': {
      color: '#1976d2',
      backgroundColor: 'rgba(0, 0, 0, 0.04)'
    }
  },
  icon: {
    '.active &': {
      color: '#1976d2'
    }
  }
}))

interface ListItemLinkProps {
  icon?: React.ReactElement
  primary: string
  to: string
}

function ListItemLink({ icon, primary, to }: ListItemLinkProps) {
  const classes = useStyles()

  const renderLink = React.useMemo(
    () =>
      React.forwardRef<any, Omit<RouterLinkProps, 'to'>>((itemProps, ref) => (
        <RouterLink to={to} ref={ref} {...itemProps} />
      )),
    [to]
  )

  return (
    <li>
      <ListItem className={classes.link} button component={renderLink}>
        {icon ? (
          <ListItemIcon className={classes.icon}>{icon}</ListItemIcon>
        ) : null}
        <ListItemText primary={primary} />
      </ListItem>
    </li>
  )
}

export default ListItemLink
