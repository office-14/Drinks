import React from 'react'
import { useLocalization, Languages } from './LocalizationContext'

function useLanguages(): Languages {
  const { current, available, change } = useLocalization()

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
