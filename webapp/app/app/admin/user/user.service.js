(function() {
	'use strict';

	angular.module('yamaApp').factory('RestUserService', restUserService);

	function restUserService(Restangular) {
		return Restangular.service('users');
	}
})();
