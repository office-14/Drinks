import React from 'react'
import { Redirect } from 'react-router-dom'

import { useAuth } from 'auth'
import { routes } from 'routing'

import SignIn from './SignIn'

function Login() {
  const { isLoggedIn } = useAuth()

  if (isLoggedIn) {
    return <Redirect to={routes.HOME} />
  }

  return <SignIn />
}

export default Login
