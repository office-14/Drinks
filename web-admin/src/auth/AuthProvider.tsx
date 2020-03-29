import React from 'react'

import FullPageSpinner from 'components/FullPageSpinner'

import { AuthContext } from './AuthContext'
import useAuthProvider from './useAuthProvider'

type AuthProviderProps = React.PropsWithChildren<{}>

function AuthProvider({ children }: AuthProviderProps) {
  const { value, isAuthenticating } = useAuthProvider()

  if (isAuthenticating) {
    return <FullPageSpinner />
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export default AuthProvider
