'use strict';

angular.module('yamaApp').factory('Roles', function (Restangular) {
	return Restangular.service('roles');
});
