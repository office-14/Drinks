import React from 'react'
import { useTranslation } from 'react-i18next'

import FullPageSpinner from 'components/FullPageSpinner'

import {
  default as LocalizationContext,
  Localization
} from './LocalizationContext'
import useLocalizationProvider from './useLocalizationProvider'

const LocalizationProvider: React.FunctionComponent = ({ children }) => {
  const value: Localization = useLocalizationProvider()

  // TODO: since I disabled i18n-next Suspense mode in i18n.defaultOptions
  // I have to handle it on my own (https://www.i18next.com/overview/api#init)
  // I need to change it to use Suspense mode
  if (!useTranslation().ready) {
    return <FullPageSpinner />
  }

  return (
    <LocalizationContext.Provider value={value}>
      {children}
    </LocalizationContext.Provider>
  )
}

export default LocalizationProvider
