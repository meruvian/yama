(function() {
	'use strict';

	angular.module('yamaApp').factory('AppService', appService);

	function appService($rootScope, ProfilePictureService, Restangular, YamaOAuth) {
		return {
			updateCurrentUser: updateCurrentUser,
			updateCurrentUserPhoto: updateCurrentUserPhoto,
			logout: logout
		};

		function logout() {
			Restangular.one('../auth/logout').get().then(function() {
				YamaOAuth.logout();
			});
		}

		function updateCurrentUser(user) {
			$rootScope.currentUser = user;
		}

		function updateCurrentUserPhoto() {
			$rootScope.currentUserPhoto = ProfilePictureService.getPhotoUrl();
		}
	}
})();
