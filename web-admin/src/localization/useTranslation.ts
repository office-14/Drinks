import { useLocalization } from './LocalizationContext'

function useTranslation() {
  return useLocalization().t
}

export default useTranslation
