(function() {
	'use strict';

	angular.module('yamaApp').factory('RestRoleService', restRoleService);

	function restRoleService(Restangular) {
		return Restangular.service('roles');
	}
})();
