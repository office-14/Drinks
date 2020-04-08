export const environment = {
  production: true,
  firebase: {
  	apiKey: "AIzaSyBBjlvvCPOD8Z96LtT4ISuGwu5eNpEEn1E",
    authDomain: "coffeedose-eaef2.firebaseapp.com",
    databaseURL: "https://coffeedose-eaef2.firebaseio.com",
    projectId: "coffeedose-eaef2",
    storageBucket: "coffeedose-eaef2.appspot.com",
    messagingSenderId: "752368906705",
    appId: "1:752368906705:web:03f9ff5a1d568c67e4b0f0",
    measurementId: "G-WMQ31M7P5K"
  },
  local_storage:  {
    prefix: 'drinks-office-14',
    storage_type: 'localStorage'
  },
  api_urls: {
    get_drinks: 'https://drinks-web-api-dev-ytpe32lyxq-uc.a.run.app/api/drinks',
    get_addins: 'https://drinks-web-api-dev-ytpe32lyxq-uc.a.run.app/api/add-ins',
    get_sizes: 'https://drinks-web-api-dev-ytpe32lyxq-uc.a.run.app/api/drinks/{drink_id}/sizes',
    post_orders: 'https://drinks-web-api-dev-ytpe32lyxq-uc.a.run.app/api/orders',
    get_order: 'https://drinks-web-api-dev-ytpe32lyxq-uc.a.run.app/api/orders/{order_id}',
  }
};