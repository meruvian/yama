(function() {
	'use strict';

	angular.module('yamaApp').config(userRoute);

	function userRoute($stateProvider) {
		$stateProvider.state('app.admin.user', {
			url: '/users',
			templateUrl: 'app/admin/user/user.list.html',
			controller: 'AdminUserListCtrl as ctrl'
		});
	}
})();
