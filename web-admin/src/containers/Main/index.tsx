import React, { StrictMode } from 'react'
import { BrowserRouter as Router } from 'react-router-dom'

import { LocalizationProvider } from 'localization'
import { ProvideAuth } from 'auth'
import App from 'containers/App'

function Main() {
  return (
    <StrictMode>
      <LocalizationProvider>
        <ProvideAuth>
          <Router>
            <App />
          </Router>
        </ProvideAuth>
      </LocalizationProvider>
    </StrictMode>
  )
}

export default Main
