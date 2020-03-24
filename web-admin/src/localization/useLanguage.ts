import React from 'react'
import LocalizationContext, { Languages } from './LocalizationContext'

function useLanguages(): Languages {
  const { current, available, change } = React.useContext(LocalizationContext)

  const languages = React.useMemo<Languages>(
    () => ({
      current,
      available,
      change
    }),
    [current, available, change]
  )

  return languages
}

export default useLanguages
