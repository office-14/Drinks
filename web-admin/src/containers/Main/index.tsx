import React, { StrictMode } from 'react'
import { BrowserRouter as Router } from 'react-router-dom'

import { LocalizationProvider } from 'localization'
import { AuthProvider } from 'auth'
import App from 'containers/App'

function Main() {
  return (
    <StrictMode>
      <LocalizationProvider>
        <AuthProvider>
          <Router>
            <App />
          </Router>
        </AuthProvider>
      </LocalizationProvider>
    </StrictMode>
  )
}

export default Main
