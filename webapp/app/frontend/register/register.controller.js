'use strict';

angular.module('yamaApp').controller('RegisterCtrl', function ($scope, $validation, Registers) {
	$scope.register = function(user, form) {
		$validation.validate(form).success(function() {
			$scope.error = false;

			Registers.post(user).then(function() {
				window.location.href = '/';
			}, function() {
				$scope.error = true;
			});
		});
	};
});
