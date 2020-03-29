// Taken from here: https://github.com/typescript-cheatsheets/react-typescript-cheatsheet#context
import React from 'react'

export function createContext<TValue>() {
  const context = React.createContext<TValue | undefined>(undefined)

  function useContext() {
    const c = React.useContext(context)

    if (c === undefined) {
      throw new Error('Cannot use the hook not inside of a Provider')
    }

    return c
  }

  return [useContext, context] as const // Make TypeScript infer a tuple, not an array of union types
}
