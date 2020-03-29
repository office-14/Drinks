import { createContext } from 'helpers'

export interface AuthDetails {
  user: firebase.User | null
  isLoggedIn: boolean
  signOut: () => Promise<void>
}

const [useContext, context] = createContext<AuthDetails>()

export const AuthContext = context
export const useAuth = useContext
