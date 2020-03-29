import React from 'react'

import { signOut, onUserChanged } from 'infrastructure/firebase'

import { AuthDetails } from './AuthContext'

type AuthProviderInfo = {
  value: AuthDetails
  isAuthenticating: boolean
}

function useAuthProvider(): AuthProviderInfo {
  const [user, setUser] = React.useState<firebase.User | null>(null)
  const [isAuthenticating, setIsAuthenticating] = React.useState(true)

  const isLoggedIn = !isAuthenticating && user !== null

  React.useEffect(() => {
    const unsubscribe = onUserChanged(firebaseUser => {
      setUser(firebaseUser)
      setIsAuthenticating(false)
    })

    return unsubscribe
  }, [])

  return {
    value: {
      user,
      isLoggedIn,
      signOut
    },
    isAuthenticating
  }
}

export default useAuthProvider
