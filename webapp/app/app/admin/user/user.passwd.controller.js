(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminUserPasswdCtrl', userPasswdController);

	function userPasswdController($uibModalInstance, $validation, user) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.user = user;
		ctrl.submit = submitUserPasswd;
		ctrl.user.password = '';

		function submitUserPasswd(user, form) {
			$validation.validate(form).success(submit);

			function submit() {
				ctrl.error = false;
				user.post('password').then($uibModalInstance.close, function() {
					ctrl.error = true;
				});
			}
		}
	}
})();
