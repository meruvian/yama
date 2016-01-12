(function() {
	'use strict';

	angular.module('yamaApp').config(profilePasswdRoute);

	function profilePasswdRoute($stateProvider) {
		$stateProvider.state('app.profile.passwd', {
			url: '/passwd',
			templateUrl: 'app/profile/passwd/passwd.form.html',
			controller: 'ProfilePasswdCtrl as ctrl'
		});
	}
})();
