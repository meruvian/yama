(function() {
	'use strict';

	angular.module('yamaApp').config(applicationRoute);

	function applicationRoute($stateProvider) {
		$stateProvider.state('app.admin.application', {
			url: '/applications',
			templateUrl: 'app/admin/application/application.list.html',
			controller: 'AdminApplicationListCtrl as ctrl'
		});
	}
})();
