export type LanguageCode = string

export interface Language {
  code: LanguageCode
  title: string
}

const languages: Language[] = [
  {
    code: 'en',
    title: 'English'
  },
  {
    code: 'ru',
    title: 'Русский'
  }
]

export default languages
