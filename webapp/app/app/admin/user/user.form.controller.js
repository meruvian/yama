(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminUserFormCtrl', userFormController);

	function userFormController($modalInstance, $validation, changeSecret, user, RestUserService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.roles = [];
		ctrl.user = user;
		ctrl.submit = submitUser;

		if (user) {
			user.confirmPassword = user.password;
		}

		function error() {
			ctrl.error = true;
		}

		function submitUser(user, form) {
			$validation.validate(form).success(submit);

			function submit() {
				ctrl.error = false;

				if (user.id) {
					user.put().then(success, error);
				} else {
					RestUserService.post(user).then(success, error);
				}
			}
		}

		function success(user) {
			$modalInstance.close(user);
		}
	}
})();
