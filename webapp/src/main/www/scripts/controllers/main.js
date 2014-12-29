'use strict';

/**
 * @ngdoc function
 * @name yama.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the yama
 */
angular.module('yama')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
