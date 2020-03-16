import React from 'react'
import { Redirect } from 'react-router-dom'

import { useAuth } from 'auth'

function Login() {
  const { isLoggedIn, signIn } = useAuth()

  React.useEffect(() => {
    if (!isLoggedIn()) signIn()
  }, [isLoggedIn, signIn])

  if (isLoggedIn()) {
    return <Redirect to="/" />
  }

  return null
}

export default Login
