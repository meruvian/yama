'use strict';

/**
 * @ngdoc overview
 * @name yamaApp
 * @description
 * # yamaApp
 *
 * Main module of the application.
 */
angular
  .module('yamaApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ngSymbiosis.repository',
    'ngSymbiosis.model',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/role', {
        templateUrl: 'views/role.html',
        controller: 'RoleCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
