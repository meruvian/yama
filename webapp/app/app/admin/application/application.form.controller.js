(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminApplicationFormCtrl', applicationFormController);

	function applicationFormController($modalInstance, $validation, application, changeSecret, RestApplicationService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.application = application;
		ctrl.changeSecret = changeSecret || false;
		ctrl.generateSecret = generateApplicationSecret;
		ctrl.submit = submitApplication;

		if (application) {
			application.redirectUri = application.registeredRedirectUris[0];
		}

		function error() {
			ctrl.error = true;
		}

		function generateApplicationSecret(application) {
			application.post('secret').then(function(a) {
				ctrl.application.secret = a.secret;
			}, error);
		}

		function submitApplication(application, form) {
			$validation.validate(form).success(submit);

			function submit() {
				application.registeredRedirectUris = [];
				application.registeredRedirectUris.push(application.redirectUri);

				ctrl.error = false;

				if (application.id) {
					application.put().then(success, error);
				} else {
					RestApplicationService.post(application).then(success, error);
				}
			}
		}

		function success(application) {
			$modalInstance.close(application);
		}
	}
})();
