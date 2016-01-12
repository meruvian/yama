(function() {
	'use strict';

	angular.module('yamaApp').config(profileRoute);

	function profileRoute($stateProvider) {
		$stateProvider.state ('app.profile', {
			url: '/profile',
			templateUrl: 'app/profile/profile.html',
			controller: 'ProfileCtrl as ctrl'
		});
	}
})();
