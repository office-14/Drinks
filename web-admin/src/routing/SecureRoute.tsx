import React from 'react'
import { Route, Redirect, RouteProps } from 'react-router-dom'

import { useAuth } from 'auth'

import { LOGIN } from './routes'

function SecureRoute(props: RouteProps) {
  const { isLoggedIn } = useAuth()

  if (!isLoggedIn) {
    return <Redirect to={LOGIN} />
  }

  return <Route {...props} />
}

export default SecureRoute
