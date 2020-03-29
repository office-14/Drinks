import React from 'react'
import { render } from '@testing-library/react'

import FullPageSpinner from './FullPageSpinner'

test('FullPageSpinner smoke test', () => {
  const { getByRole } = render(<FullPageSpinner />)

  expect(getByRole('progressbar')).not.toBeUndefined()
})
