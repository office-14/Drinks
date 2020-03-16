import React from 'react'

import AuthContext from './AuthContext'
import useProvideAuth from './useProvideAuth'
import FullPageSpinner from 'components/FullPageSpinner'

const ProvideAuth: React.FunctionComponent = ({ children }) => {
  const auth = useProvideAuth()

  React.useEffect(() => {
    if (!auth.isLoggedIn()) {
      auth.signIn()
    }
  }, [auth])

  if (!auth.isLoggedIn()) {
    return <FullPageSpinner />
  }

  return <AuthContext.Provider value={auth}>{children}</AuthContext.Provider>
}

export default ProvideAuth
