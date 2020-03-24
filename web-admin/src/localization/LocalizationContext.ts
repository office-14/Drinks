import React from 'react'
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

const LocalizationContext = React.createContext<Localization>({} as any)

export default LocalizationContext
