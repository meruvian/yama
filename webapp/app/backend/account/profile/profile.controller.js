'use strict';

angular.module('yamaApp').controller('ProfileCtrl', function ($rootScope, $scope, Users, ProfilePictures, angularPopupBoxes) {
	var users = Users.one('me');

	users.get().then(function(user) {
		$scope.user = user;
	});

	$scope.$watch('files', function() {
		if ($scope.files) {
			var file = $scope.files[0];

			ProfilePictures.uploadPhoto(file, function() {

			}, function() {
				ProfilePictures.reloadPhoto();
			});
		}
	});

	var success = function(user) {
		$rootScope.currentUser = user;
		$scope.passwd = {};

		angularPopupBoxes.alert('Update success');
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

	$scope.updatePassword = function(passwd) {
		users.password = passwd.newpass;
		users.post('password').then(success);
	};
});
