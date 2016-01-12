(function() {
	'use strict';

	angular.module('yamaApp').config(profileBasicRoute);

	function profileBasicRoute($stateProvider) {
		$stateProvider.state('app.profile.basic', {
			url: '/basic',
			templateUrl: 'app/profile/basic/basic.form.html',
			controller: 'ProfileBasicCtrl as ctrl'
		});
	}
})();
