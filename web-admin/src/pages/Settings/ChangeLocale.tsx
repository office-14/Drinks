import React from 'react'
import {
  Typography,
  Paper,
  CardContent,
  FormControl,
  FormLabel,
  RadioGroup,
  FormControlLabel,
  Radio
} from '@material-ui/core'

import { useLanguages, useTranslation } from 'localization'

function ChangeLocale() {
  const { current, available, change } = useLanguages()
  const t = useTranslation()

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    change(e.target.value)
  }

  return (
    <Paper>
      <CardContent>
        <Typography variant="h6" gutterBottom>
          {t('settings.localizationTitle')}
        </Typography>
        <Typography variant="body2" paragraph>
          {t('settings.localizationDescription')}
        </Typography>
        <FormControl component="fieldset">
          <FormLabel component="legend">
            {t('settings.languageTitle')}
          </FormLabel>
          <RadioGroup
            aria-label="currentLanguage"
            name="currentLanguage"
            value={current}
            onChange={handleChange}
          >
            {available.map(l => (
              <FormControlLabel
                key={l.code}
                value={l.code}
                control={<Radio />}
                label={l.title}
              />
            ))}
          </RadioGroup>
        </FormControl>
      </CardContent>
    </Paper>
  )
}

export default ChangeLocale
