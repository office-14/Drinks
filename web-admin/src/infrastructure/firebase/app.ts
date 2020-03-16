import * as firebase from 'firebase/app'
// import 'firebase/analytics'
import 'firebase/auth'

import config from './config'

const app = firebase.initializeApp(config)
// const analytics = app.analytics()
const auth = app.auth()

// auth.setPersistence(firebase.auth.Auth.Persistence.LOCAL)

export { app, auth }
