'use strict';

/**
 * @ngdoc service
 * @name yamaApp.Users
 * @description
 * # Users
 * Factory in the yamaApp.
 */
angular.module('yamaApp').factory('Users', function (Restangular) {
	return Restangular.service('api/users');
});
