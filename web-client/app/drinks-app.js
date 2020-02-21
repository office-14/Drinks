(function () {
  "use strict";
    angular.module('drinksApp', ['ui.router'])
      .config(configHandler)
      .controller('MainController', MainController)
      .controller('DrinksListController', DrinksListController)
      .controller('DrinkController', DrinkController)
      .controller('CartController', CartController)
      .factory('DrinksService', function ($http) {
        var  all_drinks = [];
        var selected_drink = {};
        var selected_drink_size = {};
        var selected_drink_size_price = 0;
        var selected_drink_size_volume = '';

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
            get_selected_drink_size_volume: function() {
              angular.forEach(selected_drink.sizes, function(size) {
                if (size.id == selected_drink_size.id) {
                  selected_drink_size_volume = size.volume;
                }
              });
              return selected_drink_size_volume;
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
      }).factory('CartService', function () {
        var cart_products = [];
        var qty_products = 0;
        var total_price = 0;

        var service = {
          add_product: function(draft_cart_product) {
            var new_cart_products = angular.copy(cart_products);
            var product_isset = false;
            angular.forEach(new_cart_products, function(cart_product) {
              if (draft_cart_product.drink_id == cart_product.product.drink_id 
                && draft_cart_product.size_id == cart_product.product.size_id) {
                var dr_c_p_addin_ids = [];
                var c_p_addin_ids = [];
                angular.forEach(cart_product.product.addins, function(addin) {
                  c_p_addin_ids.push(addin.id);
                });
                angular.forEach(draft_cart_product.addins, function(addin) {
                  dr_c_p_addin_ids.push(addin.id);
                });
                if (_.difference(dr_c_p_addin_ids, c_p_addin_ids).length == 0
                && _.difference(c_p_addin_ids, dr_c_p_addin_ids).length == 0) {
                  cart_product.qty += 1;
                  product_isset = true;
                }
              }
            });
            if (!product_isset) {
              new_cart_products.push(
                {
                  product: {
                    drink_id: draft_cart_product.drink_id,
                    drink_name: draft_cart_product.drink_name,   
                    drink_image: draft_cart_product.drink_image,
                    size_id: draft_cart_product.size_id,
                    size_volume: draft_cart_product.size_volume,
                    addins: draft_cart_product.addins
                  },
                  qty: 1,
                  price: draft_cart_product.price
                }
              );
            }
            angular.copy(new_cart_products, cart_products);
            qty_products += 1;
            total_price += draft_cart_product.price;
          },
          get_products: function() {
            return cart_products;
          },
          get_products_qty: function() {
            return qty_products;
          },
          get_total_price: function() {
            return total_price;
          }
        };

        return service;
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
        .state('cart', {
          url: "/",
          templateUrl: "templates/cart.html",
          controller: 'CartController'
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

    function DrinkController($scope, $http, $stateParams, DrinksService, CartService) {
      DrinksService.set_selected_drink($stateParams.id);
      $scope.drink = DrinksService.get_selected_drink();
      DrinksService.get_selected_drink_sizes();
      $scope.draft_cart_product = {
        size: DrinksService.get_first_selected_drink_size(),
        addins: []
      };

      $http.get('http://localhost:5000/api/add-ins')
      .then(function (res){
        if (res.data.payload) {
          $scope.draft_cart_product.addins = res.data.payload;
          angular.forEach($scope.draft_cart_product.addins, function(addin) {
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
        if ($scope.draft_cart_product.size && $scope.draft_cart_product.size.hasOwnProperty('id')) {
          total_price += DrinksService.get_size_price();
        }
        angular.forEach($scope.draft_cart_product.addins, function(addin) {
          if (addin.selected) {
            total_price += addin.price;
          }
        });

        return total_price;
      };

      $scope.add_to_cart = function() {
        var selected_addins = [];
        angular.forEach($scope.draft_cart_product.addins, function(addin) {
          if (addin.selected) {
            selected_addins.push(addin);
          }
        });

        var total_price = 0;
        if ($scope.draft_cart_product.size && $scope.draft_cart_product.size.hasOwnProperty('id')) {
          total_price += DrinksService.get_size_price();
        }
        angular.forEach($scope.draft_cart_product.addins, function(addin) {
          if (addin.selected) {
            total_price += addin.price;
          }
        });
        CartService.add_product(
        {
          drink_id: $scope.drink.id,
          drink_name: $scope.drink.name,
          drink_image: $scope.drink.photo_url,
          size_id: $scope.draft_cart_product.size.id,
          size_volume: DrinksService.get_selected_drink_size_volume(),
          addins: selected_addins,
          price: total_price
        }
        );
      }
    }

    function CartController($scope, CartService) {
      $scope.products = CartService.get_products();
      $scope.total_price = CartService.get_total_price();
    }

    function MainController($scope, $state, CartService) {
      this.go_to_menu = function() {
        $state.go('drinks');
      };
      this.go_to_cart = function() {
        $state.go('cart');
      };
      this.get_cart_products_qty = function() {
        return CartService.get_products_qty();
      };
    }
})();