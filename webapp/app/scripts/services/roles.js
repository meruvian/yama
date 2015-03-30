'use strict';

/**
 * @ngdoc service
 * @name yamaApp.Roles
 * @description
 * # Roles
 * Factory in the yamaApp.
 */
angular.module('yamaApp').factory('Roles', function (Restangular) {
	return Restangular.service('roles');
});
