import React from 'react'

import { auth, googleProvider } from 'infrastructure/firebase'

import { AuthDetails } from './AuthContext'

function useProvideAuth(): AuthDetails {
  const [user, setUser] = React.useState<firebase.User | null>(null)

  const signOut = async () => {
    await auth.signOut()
    setUser(null)
  }

  const signIn = async () => {
    const userCredential = await auth.signInWithPopup(googleProvider)
    setUser(userCredential.user)
  }

  const isLoggedIn = () => user !== null

  React.useEffect(() => {
    const unsubscribe = auth.onAuthStateChanged(setUser)

    return unsubscribe
  }, [])

  return {
    user,
    isLoggedIn,
    signIn,
    signOut
  }
}

export default useProvideAuth
