import React from 'react'
import { Redirect } from 'react-router-dom'

import { useAuth } from 'auth'
import { routes } from 'routing'

function Logout() {
  const { isLoggedIn, signOut } = useAuth()

  React.useEffect(() => {
    if (isLoggedIn) signOut()
  }, [isLoggedIn, signOut])

  if (!isLoggedIn) {
    return <Redirect to={routes.LOGIN} />
  }

  return null
}

export default Logout
