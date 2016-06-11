(function() {
	'use strict';

	angular.module('yamaApp').controller('ProfilePasswdCtrl', profilePasswdController);

	function profilePasswdController($validation, Dialog, RestUserService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.submit = submitUser;
		ctrl.user = RestUserService.one('me');

		function error() {
			ctrl.error = true;
		}

		function onUserLoaded(user) {
			ctrl.user = user;
			ctrl.confirmPassword = user.password;

			Dialog.alert('Update success');
		}

		function submitUser(user, form) {
			$validation.validate(form).success(submit);

			function submit() {
				ctrl.error = false;

				user.post('password').then(onUserLoaded, error);
			}
		}
	}
})();
