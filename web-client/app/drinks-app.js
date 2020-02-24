(function () {
  "use strict";
    angular.module('drinksApp', ['ui.router', 'ngMaterial'])
      .config(configHandler)
      .controller('MainController', MainController)
      .controller('DrinksListController', DrinksListController)
      .controller('DrinkController', DrinkController)
      .controller('CartController', CartController)
      .controller('OrderController', OrderController)
      .factory('NoticeHandler', function($mdToast) {
        var service = {
          show_success: function(message) {
            $mdToast.show($mdToast.simple()
              .textContent(message)
              .position('top right')
              .theme('success-toast')
              .hideDelay(3000)
            );
          },
          show_error: function(message) {
            $mdToast.show($mdToast.simple()
              .textContent(message)
              .position('top right')
              .theme('error-toast')
              .hideDelay(3000)
            );
          }
        }

        return service;
      })
      .factory('ErrorHandler', function(NoticeHandler) {
        var service = {
          show_error: function(response) {
            if (response.status == 404) {
              NoticeHandler.show_error('Ошибка при получении данных: ' + response.data.title);
            } else {
              NoticeHandler.show_error('Ошибка при обращении к серверу. Код ошибки: ' + response.data.title);
            }
          }
        }

        return service;
      })
      .factory('DrinksService', function ($http, ErrorHandler) {
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
                  $http.get('http://localhost:5000/api/drinks/'+drink.id+'/sizes')
                  .then(function (res){
                    if (res.data.payload) {
                      drink.sizes = res.data.payload;
                      angular.copy(drink, selected_drink);
                      angular.copy(selected_drink.sizes[0], selected_drink_size);
                    } else {
                      ErrorHandler.show_error(res);
                    }
                  })
                  .catch(function (res) {
                    ErrorHandler.show_error(res);
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

        var generate_alias = function(drink_id, size_id, addins_ids) {
          return drink_id.toString(10) + '_' + size_id.toString(10) + '_' + addins_ids.join('#');
        }

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
                  cart_product.qty += draft_cart_product.qty;
                  product_isset = true;
                }
              }
            });
            if (!product_isset) {
              var addins_ids = [];
              angular.forEach(draft_cart_product.addins, function(addin) {
                addins_ids.push(addin.id);
              });
              new_cart_products.push(
                {
                  alias: generate_alias(draft_cart_product.drink_id, draft_cart_product.size_id, addins_ids),
                  product: {
                    drink_id: draft_cart_product.drink_id,
                    drink_name: draft_cart_product.drink_name,   
                    drink_image: draft_cart_product.drink_image,
                    size_id: draft_cart_product.size_id,
                    size_volume: draft_cart_product.size_volume,
                    addins: draft_cart_product.addins
                  },
                  qty: draft_cart_product.qty,
                  price: draft_cart_product.price
                }
              );
            }
            angular.copy(new_cart_products, cart_products);
          },
          remove_product: function(product_alias) {
            var new_cart_products = [];
            angular.forEach(cart_products, function(cart_product) {
              if (cart_product.alias != product_alias) {
                new_cart_products.push(cart_product);
              }
            });
            angular.copy(new_cart_products, cart_products);
          },
          generate_products_request_body: function() {
            var products_request_body = [];
            angular.forEach(cart_products, function(cart_product) {
              for (var i = 0; i < cart_product.qty; i++) {
                var addins_ids = [];
                angular.forEach(cart_product.product.addins, function(addin) {
                  addins_ids.push(addin.id);
                });
                products_request_body.push(
                  {
                    "drink_id": cart_product.product.drink_id,
                    "size_id": cart_product.product.size_id,
                    "add-ins": addins_ids
                  }
                );
              }
            });
            return products_request_body;
          },
          get_products: function() {
            return cart_products;
          },
          get_products_qty: function() {
            qty_products = 0;
            angular.forEach(cart_products, function(cart_product) {
              qty_products += cart_product.qty;
            });
            return qty_products;
          },
          get_total_price: function() {
            total_price = 0;
            angular.forEach(cart_products, function(cart_product) {
              total_price += cart_product.qty * cart_product.price;
            });
            return total_price;
          },
          clear_cart: function() {
            angular.copy([], cart_products);
          }
        };

        return service;
      }).factory('OrderService', function () {
        var current_order = {};

        var service = {
          set_order: function(order, order_products) {
            var new_order = {
              id: order.id,
              status_code: order.status_code,
              status_name: order.status_name,
              order_number: order.order_number,
              total_price: order.total_price,
              order_products: order_products
            };
            angular.copy(new_order, current_order);
          },
          get_order: function() {
            return current_order;
          },
          if_order_exist: function() {
            if (current_order.hasOwnProperty('id')) {
              return true;
            }
            return false;
          },
          clear_order: function() {
            angular.copy({}, current_order);
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
        .state('order', {
          url: "/",
          templateUrl: "templates/order.html",
          controller: 'OrderController'
        })
    }

    function DrinksListController($scope, $http, DrinksService, ErrorHandler) {
      $scope.drinks = [];

      $http.get('http://localhost:5000/api/drinks')
      .then(function (res){
        if (res.data.payload) {
          DrinksService.set_drinks(res.data.payload);
          $scope.drinks = DrinksService.get_drinks();
        } else {
          ErrorHandler.show_error(res);
        }
      })
      .catch(function (res) {
        ErrorHandler.show_error(res);
      });
    }

    function DrinkController($scope, $http, $stateParams, DrinksService, CartService, NoticeHandler, ErrorHandler) {
      DrinksService.set_selected_drink($stateParams.id);
      $scope.drink = DrinksService.get_selected_drink();
      DrinksService.get_selected_drink_sizes();
      $scope.draft_cart_product = {
        size: DrinksService.get_first_selected_drink_size(),
        addins: [],
        qty: 1
      };

      $http.get('http://localhost:5000/api/add-ins')
      .then(function (res){
        if (res.data.payload) {
          $scope.draft_cart_product.addins = res.data.payload;
          angular.forEach($scope.draft_cart_product.addins, function(addin) {
            addin.selected = false;
          });
        } else {
          ErrorHandler.show_error(res);
        }
      })
      .catch(function (res) {
        ErrorHandler.show_error(res);
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
          price: total_price,
          qty: $scope.draft_cart_product.qty
        }
        );
        NoticeHandler.show_success('Товар успешно добавлен в корзину!');
      }

      $scope.change_draft_cart_product_qty = function() {
        if (typeof $scope.draft_cart_product.qty == 'undefined') {
          $scope.draft_cart_product.qty = 1;
        }
      }
    }

    function CartController($scope, $http, CartService, OrderService, NoticeHandler, ErrorHandler) {
      $scope.products = CartService.get_products();
      $scope.get_total_price = function() {
        return CartService.get_total_price();
      }
      $scope.remove_from_cart = function(product_alias) {
        CartService.remove_product(product_alias);
      }
      $scope.change_product_qty = function(product) {
        if (typeof product.qty == 'undefined') {
          product.qty = 1;
        }
      }
      $scope.create_order = function() {
        if (OrderService.if_order_exist()) {

        } else {
          var products_request_body = CartService.generate_products_request_body();
          var post_data = {
            "drinks": products_request_body
          };
          $http.post('http://localhost:5000/api/orders', post_data)
          .then(function (res){
            if (res.data.payload) {
              OrderService.set_order(res.data.payload, CartService.get_products());
              CartService.clear_cart();
              NoticeHandler.show_success('Заказ успешно сформирован!');
            } else {
              ErrorHandler.show_error(res);
            }
          })
          .catch(function (res) {
            ErrorHandler.show_error(res);
          });
        }
      }
      $scope.if_order_exist = function() {
        return OrderService.if_order_exist();
      };
    }

    function OrderController($scope, $http, OrderService, ErrorHandler) {
      $scope.order = OrderService.get_order();
      $scope.if_order_exist = function() {
        return OrderService.if_order_exist();
      };
      $scope.finish_order = function() {
        if (OrderService.if_order_exist()) {
          var current_order = OrderService.get_order();
          $http.post('http://localhost:5000/api/orders/'+current_order.id+'/finish')
          .then(function (res){
            if (res.status == 204) {
              OrderService.clear_order();
            } else {
              ErrorHandler.show_error(res);
            }
          })
          .catch(function (res) {
            ErrorHandler.show_error(res);
          });
        }
      }
    }

    function MainController($scope, $state, CartService, OrderService) {
      this.go_to_menu = function() {
        $state.go('drinks');
      };
      this.go_to_cart = function() {
        $state.go('cart');
      };
      this.go_to_order = function() {
        $state.go('order');
      };
      this.get_cart_products_qty = function() {
        return CartService.get_products_qty();
      };
      this.if_order_exist = function() {
        return OrderService.if_order_exist();
      };
    }
})();