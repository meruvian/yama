'use strict';

/**
 * @ngdoc function
 * @name yama.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the yama
 */
angular.module('yama')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
