(function() {
	'use strict';

	angular.module('yamaApp').run(checkAuthorization);

	function checkAuthorization($rootScope, AppService, ProfilePictureService, RestUserService, YamaOAuth) {
		$rootScope.$state = {};

		if (!YamaOAuth.isAuthorized()) {
			YamaOAuth.login();
		} else {
			if (!$rootScope.currentUser) {
				RestUserService.one('me').get().then(setCurrentUser);
			}
		}

		$rootScope.$on('$stateChangeStart', onStateChangeStart);
		$rootScope.$on('oauth:logout', YamaOAuth.login);
		$rootScope.$on('oauth:unauthorized', YamaOAuth.login);

		function onStateChangeStart(event, toState, toParams) {
			if (!YamaOAuth.isAuthorized()) {
				event.preventDefault();

				$rootScope.$state.toState = toState;
				$rootScope.$state.toState.params = toParams;

				YamaOAuth.login();
			}
		}

		function setCurrentUser(user) {
			AppService.updateCurrentUser(user);
			AppService.updateCurrentUserPhoto();
		}
	}
})();
