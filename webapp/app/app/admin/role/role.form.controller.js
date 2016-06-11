(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminRoleFormCtrl', roleFormController);

	function roleFormController($uibModalInstance, $validation, changeSecret, role, RestRoleService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.role = role;
		ctrl.submit = submitRole;

		function error() {
			ctrl.error = true;
		}

		function submitRole(role, form) {
			$validation.validate(form).success(submit);

			function submit() {
				ctrl.error = false;

				if (role.id) {
					role.put().then(success, error);
				} else {
					RestRoleService.post(role).then(success, error);
				}
			}
		}

		function success(role) {
			$uibModalInstance.close(role);
		}
	}
})();
