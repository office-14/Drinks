import React, { StrictMode } from 'react'
import { BrowserRouter as Router } from 'react-router-dom'

import { ProvideAuth } from 'auth'
import App from 'containers/App'

function Main() {
  return (
    <StrictMode>
      <ProvideAuth>
        <Router>
          <App />
        </Router>
      </ProvideAuth>
    </StrictMode>
  )
}

export default Main
