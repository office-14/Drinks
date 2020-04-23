import React from 'react'
import { render } from '@testing-library/react'

import { onUserChanged } from 'infrastructure/firebase'

import AuthProvider from './AuthProvider'

jest.mock('infrastructure/firebase')
const mockedOnUserChanged = onUserChanged as jest.MockedFunction<
  typeof onUserChanged
>

describe('AuthProvider', () => {
  it('shows loading indicator when user information is loading', () => {
    mockedOnUserChanged.mockImplementationOnce(() => () => {})

    const { getByRole } = render(<AuthProvider />)

    expect(getByRole('progressbar')).not.toBeUndefined()
  })

  xit('displays children when user loading information is finished', () => {
    mockedOnUserChanged.mockImplementationOnce((cb) => {
      ;(cb as (a: any) => any)({
        getIdToken() {
          return Promise.resolve('token')
        },
      })
      return () => {}
    })

    const { getByText } = render(<AuthProvider>User loaded</AuthProvider>)

    expect(getByText(/load/)).toHaveTextContent('User loaded')
  })
})
