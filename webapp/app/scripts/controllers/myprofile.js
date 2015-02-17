'use strict';

/**
 * @ngdoc function
 * @name yamaApp.controller:MyprofileCtrl
 * @description
 * # MyprofileCtrl
 * Controller of the yamaApp
 */
angular.module('yamaApp').controller('MyProfileCtrl', function ($rootScope, $scope, Users, ProfilePictures) {
	var users = Users.one('me');

	users.get().then(function(user) {
		$scope.user = user;
	});

	$scope.$watch('files', function() {
		var file = $scope.files[0];

		ProfilePictures.uploadPhoto(file, function() {

		}, function() {
			ProfilePictures.reloadPhoto();
		});
	});

	var success = function(user) {
		$rootScope.currentUser = user;
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
