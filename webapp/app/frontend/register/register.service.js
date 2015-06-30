'use strict';

angular.module('yamaApp').factory('Registers', function (Restangular) {
	return Restangular.service('signup');
});
