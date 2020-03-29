import React from 'react'

import { createContext } from './createContext'
import { render } from '@testing-library/react'

beforeEach(() => {
  // when the error's thrown a bunch of console.errors are called even though
  // the error boundary handles the error. This makes the test output noisy,
  // so we'll mock out console.error
  jest.spyOn(console, 'error')
  // @ts-ignore
  console.error.mockImplementation(() => {})
})

afterEach(() => {
  // @ts-ignore
  console.error.mockRestore()
})

describe('createContext()', () => {
  describe('useContext() hook', () => {
    const [useMyContext, MyContext] = createContext<string>()

    function ThrowableComponent() {
      const value = useMyContext()

      return <div>{value}</div>
    }

    test('throws exception when used outside of a context', () => {
      expect(() => render(<ThrowableComponent />)).toThrow()
    })

    test('returns value of a context when used inside of a context', () => {
      const { getByText } = render(
        <MyContext.Provider value="some value">
          <ThrowableComponent />
        </MyContext.Provider>
      )

      expect(getByText(/some/)).toHaveTextContent('some value')
    })
  })
})
