import React from 'react'
import { Route, Redirect, RouteProps } from 'react-router-dom'

import { useAuth } from 'auth'

import { LOGIN } from './routes'

type SecureRouteProps = RouteProps

function SecureRoute(props: SecureRouteProps) {
  const { isLoggedIn } = useAuth()

  if (!isLoggedIn) {
    return <Redirect to={LOGIN} />
  }

  return <Route {...props} />
}

export default SecureRoute
