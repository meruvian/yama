(function() {
	'use strict';

	angular.module('yamaApp').config(applicationRoute);

	function applicationRoute($stateProvider) {
		$stateProvider.state('app.admin.role', {
			url: '/roles',
			templateUrl: 'app/admin/role/role.list.html',
			controller: 'AdminRoleListCtrl as ctrl'
		});
	}
})();
