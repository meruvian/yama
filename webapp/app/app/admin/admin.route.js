(function() {
	'use strict';

	angular.module('yamaApp').config(adminRoute);

	function adminRoute($stateProvider) {
		$stateProvider.state('app.admin', {
			url: '/admin',
			templateUrl: 'app/admin/admin.html',
			controller: 'AdminCtrl as ctrl'
		});
	}
})();
