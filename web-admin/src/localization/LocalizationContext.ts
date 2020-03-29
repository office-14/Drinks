import { createContext } from 'helpers'

import { Language, LanguageCode } from './languages'

export interface Languages {
  current: LanguageCode
  available: Language[]
  change: (code: LanguageCode) => void
}

export interface Translation {
  t: (...args: any) => string
}

export interface Localization extends Languages, Translation {}

const [useContext, context] = createContext<Localization>()

export const useLocalization = useContext
export const LocalizationContext = context
