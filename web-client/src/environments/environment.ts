// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
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
    get_drinks: 'http://localhost:5000/api/drinks',
    get_addins: 'http://localhost:5000/api/add-ins',
    get_sizes: 'http://localhost:5000/api/drinks/{drink_id}/sizes',
    post_orders: 'http://localhost:5000/api/orders',
    get_order: 'http://localhost:5000/api/orders/{order_id}',
    get_last_order_url: 'http://localhost:5000/api/user/orders/last',
    get_last_order_status_url: 'http://localhost:5000/api/user/orders/last/status',
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
