'use strict';

/**
 * @ngdoc function
 * @name yamaApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the yamaApp
 */
angular.module('yamaApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
