import { onUserChanged } from 'infrastructure/firebase'

const defaultHeaders = {
  Accept: 'application/json',
  Authorization: '',
}

function wrappedFetch(input: RequestInfo, init?: RequestInit) {
  return fetch(input, {
    ...init,
    headers: { ...defaultHeaders, ...init?.headers },
  })
}

onUserChanged(async (user) => {
  if (user !== null) {
    const idToken = await user.getIdToken()
    defaultHeaders.Authorization = `Bearer ${idToken}`
  } else {
    defaultHeaders.Authorization = ''
  }
})

export default wrappedFetch
