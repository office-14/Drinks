import React from 'react'

import FullPageSpinner from 'components/FullPageSpinner'

import AuthContext from './AuthContext'
import useProvideAuth from './useProvideAuth'

const ProvideAuth: React.FunctionComponent = ({ children }) => {
  const auth = useProvideAuth()

  if (auth.isAuthenticating) {
    return <FullPageSpinner />
  }

  return <AuthContext.Provider value={auth}>{children}</AuthContext.Provider>
}

export default ProvideAuth
