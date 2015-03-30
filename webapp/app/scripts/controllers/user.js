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
					return user || {};
				}
			}
		});

		modal.result.then(function(u) {
			$scope.searchParams.q = u.username;
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

	$scope.addRole = function(user) {
		var modal = $modal.open({
			templateUrl: 'userEditRoleForm.html',
			controller: 'UserEditRoleFormCtrl',
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
	$scope.user = user;

	$scope.isNew = !angular.isDefined($scope.user);

	var success = function(u) {
		$modalInstance.close(u);
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

		u.post('password').then(function() {
			$modalInstance.close();
		}, function() {
			$scope.error = true;
		});
	};
}).controller('UserEditRoleFormCtrl', function($scope, $modalInstance, $cacheFactory, Roles, user) {
	$scope.user = user;
	$scope.roles = [];

	var invalidateCache = function() {
		$cacheFactory.get('$http').remove(user.one('roles').getRequestedUrl());
	};

	$scope.loadRoles = function(search) {
		Roles.getList({ q: search }).then(function(roles) {
			$scope.roles = roles;
		});
	};

	$scope.addRole = function(role) {
		user.one('roles', role.id).put().then(function() {
			invalidateCache();
		});
	};

	$scope.removeRole = function(role) {
		user.one('roles', role.id).remove().then(function() {
			invalidateCache();
		});
	};

	$scope.done = $modalInstance.close;
});
