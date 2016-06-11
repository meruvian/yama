(function() {
	'use strict';

	angular.module('yamaApp').controller('ProfileBasicCtrl', profileBasicController);

	function profileBasicController($rootScope, $timeout, $validation, Dialog, AppService, RestUserService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.submit = submitUser;

		RestUserService.one('me').get().then(onUserLoaded);

		function error() {
			ctrl.error = true;
		}

		function onUserLoaded(user) {
			ctrl.user = user;
			ctrl.username = user.username;
			ctrl.email = user.email;

			AppService.updateCurrentUser(user);
		}

		function submitUser(user, form) {
			$validation.validate(form).success(submit);

			function submit() {
				ctrl.error = false;

				if (user.id) {
					user.put().then(onUserLoaded, error);
				} else {
					RestUserService.post(user).then(onUserLoaded, error);
				}
			}
		}
	}
})();
