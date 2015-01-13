'use strict';

/**
 * @ngdoc function
 * @name yamaApp.controller:UserCtrl
 * @description
 * # UserCtrl
 * Controller of the yamaApp
 */
angular.module('yamaApp').controller('UserCtrl', function ($scope, $modal, $location, Users, angularPopupBoxes) {
	$scope.searchParams = $location.search();

	// Load list on page loaded
	Users.getList($scope.searchParams).then(function(users) {
		$scope.users = users;
		$scope.page = users.meta.number + 1;
	});

	// Search form submitted or page changed
	$scope.search = function(p) {
		p.hash = p.hash || 0;
		p.hash++;
		p.page = $scope.page - 1;

		$location.search(p);
	};

	// ID clicked, open popup form dialog
	$scope.openForm = function(user) {
		var modal = $modal.open({
			templateUrl: 'userForm.html',
			controller: 'UserFormCtrl',
			size: 'lg',
			resolve: {
				user: function() {
					return user;
				}
			}
		});

		modal.result.then(function() {
			$scope.search($scope.searchParams);
		});
	};

	$scope.changePasswd = function(user) {
		var modal = $modal.open({
			templateUrl: 'userChangePasswdForm.html',
			controller: 'UserChangePasswdFormCtrl',
			size: 'lg',
			resolve: {
				user: function() {
					return user;
				}
			}
		});

		modal.result.then(function() {
			$scope.search($scope.searchParams);
		});
	};

	// Open popup confirmation and delete user if user choose yes
	$scope.remove = function(user) {
		angularPopupBoxes.confirm('Are you sure want to delete this data?').result.then(function() {
			user.remove().then(function() {
				$scope.search($scope.searchParams);
			});
		});
	};
}).controller('UserFormCtrl', function($scope, $modalInstance, Users, user) {
	if (user) {
		$scope.user = user;
	}

	$scope.isNew = !angular.isDefined($scope.user);

	var success = function() {
		$modalInstance.close();
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
}).controller('UserChangePasswdFormCtrl', function($scope, $modalInstance, user) {
	user.password = '';
	$scope.user = user;

	$scope.submit = function(u) {
		$scope.error = false;

		console.log(u.password);

		u.post('password').then(function() {
			$modalInstance.close();
		}, function() {
			$scope.error = true;
		});
	};
});
