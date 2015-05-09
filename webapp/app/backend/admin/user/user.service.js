'use strict';

angular.module('yamaApp').factory('Users', function (Restangular) {
	return Restangular.service('users');
});
