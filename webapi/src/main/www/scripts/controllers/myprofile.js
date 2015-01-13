'use strict';

/**
 * @ngdoc function
 * @name yamaApp.controller:MyprofileCtrl
 * @description
 * # MyprofileCtrl
 * Controller of the yamaApp
 */
angular.module('yamaApp').controller('MyprofileCtrl', function ($scope, Users) {
	$scope.refresh = function() {
		Users.one('me').get().then(function(user) {
			$scope.user = user;
		});
	};

	$scope.refresh();

	var success = function() {
		$scope.refresh();
	};

	var error = function() {
		$scope.error = true;
	};

	$scope.submit = function(user) {
		$scope.error = false;

		if (user.id) {
			user.put().then(success, error);
		} else {
			Users.post(user).then(success, error);
		}
	};
});
