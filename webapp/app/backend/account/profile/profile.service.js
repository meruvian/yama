'use strict';

angular.module('yamaApp').factory('Profiles', function (Restangular) {
	return Restangular.service('users/me');
});
