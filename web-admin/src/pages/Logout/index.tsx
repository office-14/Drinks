import React from 'react'
import { Redirect } from 'react-router-dom'

import { useAuth } from 'auth'

function Logout() {
  const { isLoggedIn, signOut } = useAuth()

  React.useEffect(() => {
    if (isLoggedIn()) signOut()
  }, [isLoggedIn, signOut])

  if (!isLoggedIn()) {
    return <Redirect to="/login" />
  }

  return null
}

export default Logout
