'use strict';

/**
 * @ngdoc function
 * @name yamaAppApp.controller:RoleCtrl
 * @description
 * # RoleCtrl
 * Controller of the yamaAppApp
 */
angular.module('yamaApp').controller('RoleCtrl', function ($scope, $modal, $location, Roles, angularPopupBoxes) {
	$scope.searchParams = $location.search();

	// Load list on page loaded
	Roles.getList($scope.searchParams).then(function(roles) {
		$scope.roles = roles;
		$scope.page = roles.meta.number + 1;
	});

	// Search form submitted or page changed
	$scope.search = function(p) {
		p.hash = p.hash || 0;
		p.hash++;
		p.page = $scope.page - 1;

		$location.search(p);
	};

	// ID clicked, open popup form dialog
	$scope.openForm = function(role) {
		var modal = $modal.open({
			templateUrl: 'roleForm.html',
			controller: 'RoleFormCtrl',
			size: 'lg',
			resolve: {
				role: function() {
					return role;
				}
			}
		});

		modal.result.then(function() {
			$scope.search($scope.searchParams);
		});
	};

	// Open popup confirmation and delete user if user choose yes
	$scope.remove = function(role) {
		angularPopupBoxes.confirm('Are you sure want to delete this data?').result.then(function() {
			role.remove().then(function() {
				$scope.search($scope.searchParams);
			});
		});
	};
}).controller('RoleFormCtrl', function($scope, $modalInstance, Roles, role) {
	if (role) {
		$scope.role = role;
	}

	var success = function() {
		$modalInstance.close();
	};

	var error = function() {
		$scope.error = true;
	};

	$scope.submit = function(role) {
		$scope.error = false;

		if (role.id) {
			role.put().then(success, error);
		} else {
			Roles.post(role).then(success, error);
		}
	};
});
