import React from 'react'

import AuthContext from './AuthContext'

function useAuth() {
  return React.useContext(AuthContext)
}

export default useAuth
