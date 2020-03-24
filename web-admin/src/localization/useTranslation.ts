import React from 'react'

import LocalizationContext from './LocalizationContext'

function useTranslation() {
  return React.useContext(LocalizationContext).t
}

export default useTranslation
