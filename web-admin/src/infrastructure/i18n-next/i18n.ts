import i18n, { InitOptions } from 'i18next'
import { initReactI18next } from 'react-i18next'
import LoadTranslationFromBackend from 'i18next-xhr-backend'

const defaultOptions: InitOptions = {
  debug: process.env.NODE_ENV === 'development',
  lng: 'en',
  fallbackLng: 'en',
  ns: ['translations'],
  defaultNS: 'translations',
  interpolation: {
    escapeValue: false // not needed for react as it escapes by default
  },
  backend: {
    loadPath: '/i18n/{{ns}}/{{lng}}.json'
  },
  react: {
    useSuspense: false
  }
}

function createI18n(options?: InitOptions) {
  const instance = i18n
    .createInstance()
    .use(LoadTranslationFromBackend)
    .use(initReactI18next)

  instance.init({ ...defaultOptions, ...options })

  return instance
}

export const i18nInstance = createI18n()

export const t = i18nInstance.t.bind(i18nInstance)

export const changeLanguage = i18nInstance.changeLanguage.bind(i18nInstance)
