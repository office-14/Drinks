(function () {
  "use strict";
    angular.module('drinksApp', ['ui.router'])
      .config(configHandler)
      .controller('DrinksListController', DrinksListController)
      .controller('DrinkController', DrinkController)
      .factory('DrinksService', function ($http) {
        var  all_drinks = [];
        var selected_drink = {};
        var selected_drink_size = {};
        var selected_drink_size_price = 0;

        var service = {
            set_drinks: function (drinks) {
              all_drinks = drinks;

              return true;
            },
            get_drinks: function () {
                return all_drinks;
            },
            set_selected_drink: function (id) {
              angular.forEach(all_drinks, function(drink) {
                if (drink.id == id) {
                  selected_drink = drink;
                }
              });

              return true;
            },
            get_selected_drink: function () {
              return selected_drink;
            },
            get_selected_drink_sizes: function () {
              var drink = angular.copy(selected_drink);
              if (drink.hasOwnProperty('id')) {
                if (!drink.hasOwnProperty('sizes')) {
                  $http.get('http://localhost:5000/api/drinks/'+ drink.id +'/sizes')
                  .then(function (res){
                    if (res.data.payload) {
                      drink.sizes = res.data.payload;
                      angular.copy(drink, selected_drink);
                      angular.copy(selected_drink.sizes[0], selected_drink_size);
                    }
                  })
                  .catch(function (res) {
                  })
                  .finally(function () {
                  });
                }
              }
            },
            get_first_selected_drink_size: function() {
              return selected_drink_size;
            },
            get_size_price: function() {
              angular.forEach(selected_drink.sizes, function(size) {
                if (size.id == selected_drink_size.id) {
                  selected_drink_size_price = size.price;
                }
              });
              return selected_drink_size_price;
            }
        };

        return service;
      }).factory('DraftCartDrinkService', function () {
        var  drink = {
          drink_id: 0,
          size_id: 0,
          add_ins: []
        };

        var service = {
            set_drink_id: function (drink_id) {
              drink.drink_id = drink_id;

              return true;
            },
            set_size_id: function (size_id) {
                drink.size_id = size_id;
                return true;
            },
            add_addin: function (addin_id) {
              if (!drink.add_ins.includes(addin_id)) {
                drink.add_ins.push(addin_id);
              }

              return true;
            },
            remove_addin: function (addin_id) {
              if (drink.add_ins.includes(addin_id)) {
                drink.add_ins.splice(drink.add_ins.indexOf(addin_id), 1);
              }

              return true;
            }
        };

        return drink;
      });

    function configHandler($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.otherwise('/');
      $stateProvider
        .state('drinks', {
          url: "/",
          templateUrl: "templates/list.html",
          controller: 'DrinksListController'
        })
        .state('drink', {
          url: "/",
          params: {
            id: 0
          },
          templateUrl: "templates/drink.html",
          controller: 'DrinkController'
        })
    }

    function DrinksListController($scope, $http, DrinksService) {
      $scope.drinks = [];

      $http.get('http://localhost:5000/api/drinks')
      .then(function (res){
        if (res.data.payload) {
          DrinksService.set_drinks(res.data.payload);
          $scope.drinks = DrinksService.get_drinks();
        }
      })
      .catch(function (res) {
      })
      .finally(function () {
      });
    }

    function DrinkController($scope, $http, $stateParams, DrinksService, DraftCartDrinkService) {
      DrinksService.set_selected_drink($stateParams.id);
      $scope.drink = DrinksService.get_selected_drink();
      DrinksService.get_selected_drink_sizes();
      $scope.DraftCartDrink = {
        size: DrinksService.get_first_selected_drink_size(),
        addins: []
      };

      $scope.add_ins = [];
      $http.get('http://localhost:5000/api/add-ins')
      .then(function (res){
        if (res.data.payload) {
          $scope.add_ins = res.data.payload;
          angular.forEach($scope.add_ins, function(addin) {
            addin.selected = false;
          });
        }
      })
      .catch(function (res) {
      })
      .finally(function () {
      });

      $scope.get_selected_price = function() {
        var total_price = 0;
        if ($scope.DraftCartDrink.size && $scope.DraftCartDrink.size.hasOwnProperty('id')) {
          total_price += DrinksService.get_size_price();
        }
        angular.forEach($scope.add_ins, function(addin) {
          if (addin.selected) {
            total_price += addin.price;
          }
        });

        return total_price;
      }

      $scope.add_to_cart = function() {
      }
    }
})();