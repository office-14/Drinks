import React from 'react'

import { changeLanguage, t } from 'infrastructure/i18n-next'

import { default as languages, LanguageCode } from './languages'
import { Localization } from './LocalizationContext'

function useLocalizationProvider(): Localization {
  const [language, setLanguage] = React.useState<LanguageCode>(
    languages[0].code
  )

  const change = React.useCallback(
    async newLang => {
      // TODO: I don't know what to do when language won't be loaded.
      // Hope for the best.
      await changeLanguage(newLang)
      setLanguage(newLang)
    },
    [setLanguage]
  )

  return {
    current: language,
    change,
    available: languages,
    t
  }
}

export default useLocalizationProvider
