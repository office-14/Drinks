import React from 'react'

export type AuthDetails = {
  user: firebase.User | null
  isLoggedIn: () => boolean
  signIn: () => Promise<void>
  signOut: () => Promise<void>
}

const initialState: AuthDetails = {
  user: null,
  isLoggedIn: () => false,
  signIn: () => Promise.resolve(),
  signOut: () => Promise.resolve()
}

const AuthContext = React.createContext<AuthDetails>(initialState)

export default AuthContext
