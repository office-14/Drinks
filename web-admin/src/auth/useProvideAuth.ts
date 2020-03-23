import React from 'react'

import { auth } from 'infrastructure/firebase'

import { AuthDetails } from './AuthContext'

function signOut() {
  return auth.signOut()
}

function useProvideAuth(): AuthDetails {
  const [user, setUser] = React.useState<firebase.User | null>(null)
  const [isAuthenticating, setIsAuthenticating] = React.useState(true)

  const isLoggedIn = !isAuthenticating && user !== null

  React.useEffect(() => {
    const unsubscribe = auth.onAuthStateChanged(firebaseUser => {
      setUser(firebaseUser)
      setIsAuthenticating(false)
    })

    return unsubscribe
  }, [])

  return {
    user,
    isLoggedIn,
    signOut,
    isAuthenticating
  }
}

export default useProvideAuth
