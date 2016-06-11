(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminUserRoleCtrl', userRoleController);

	function userRoleController($cacheFactory, $uibModalInstance, user, RestRoleService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.addRole = addUserRole;
		ctrl.done = $uibModalInstance.close;
		ctrl.loadRoles = loadUserRoles;
		ctrl.removeRole = removeUserRole;
		ctrl.roles = [];
		ctrl.user = user;
		
		function addUserRole(role) {
			user.one('roles', role.id).put().then(invalidateCache);
		}

		function invalidateCache() {
			$cacheFactory.get('$http').remove(user.one('roles').getRequestedUrl());
		}

		function loadUserRoles(search) {
			RestRoleService.getList({ q: search }).then(onRolesLoaded);

			function onRolesLoaded(roles) {
				ctrl.roles = roles;
			}
		}

		function removeUserRole(role) {
			user.one('roles', role.id).remove().then(invalidateCache);
		}
	}
})();
