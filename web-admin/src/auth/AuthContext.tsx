import React from 'react'

export interface AuthDetails {
  user: firebase.User | null
  isLoggedIn: boolean
  signOut: () => Promise<void>

  // TODO: there is only one consumer of this value (ProvideAuth.tsx)
  // How about hiding it from other clients?
  isAuthenticating: boolean
}

const AuthContext = React.createContext<AuthDetails>({} as any)

export default AuthContext
