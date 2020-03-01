import React, { StrictMode } from 'react'
import { BrowserRouter as Router } from 'react-router-dom'

import App from 'containers/App'

function Main() {
  return (
    <StrictMode>
      <Router>
        <App />
      </Router>
    </StrictMode>
  )
}

export default Main
