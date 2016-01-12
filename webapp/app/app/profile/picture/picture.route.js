(function() {
	'use strict';

	angular.module('yamaApp').config(profilePictureRoute);

	function profilePictureRoute($stateProvider) {
		$stateProvider.state('app.profile.picture', {
			url: '/picture',
			templateUrl: 'app/profile/picture/picture.form.html',
			controller: 'ProfilePictureCtrl as ctrl'
		});
	}
})();
