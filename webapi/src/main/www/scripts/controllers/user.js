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

		angular.forEach(users, function(user) {
			user.getList('roles').then(function(roles) {
				user.roles = roles;
			});
		});
	});

	// Search form submitted or page changed
	$scope.search = function(p) {
		p.cache = p.cache || 0;
		p.cache++;
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
}).controller('UserFormCtrl', function($scope, $modalInstance, Users, Roles, user) {
	$scope.roles = [];

	if (user) {
		$scope.user = user;

		user.roles = user.roles || [];
		$scope.roles = user.roles;
	}

	$scope.isNew = !angular.isDefined($scope.user);

	var closeModal = function() {
		$modalInstance.close();
	};

	var success = function() {
		// update Roles
		user.one('roles').remove().then(function() {
			angular.forEach(user.roles, function(role) {
				user.one('roles', role.id).put();
			});
		}, closeModal);

		closeModal();
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

	$scope.loadRoles = function(search) {
		Roles.getList({ q: search }).then(function(roles) {
			$scope.roles = roles;
		});
	};
}).controller('UserChangePasswdFormCtrl', function($scope, $modalInstance, user) {
	user.password = '';
	$scope.user = user;

	$scope.submit = function(u) {
		$scope.error = false;

		u.post('password').then(function() {
			$modalInstance.close();
		}, function() {
			$scope.error = true;
		});
	};
});
