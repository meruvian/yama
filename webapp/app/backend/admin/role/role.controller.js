'use strict';

angular.module('yamaApp').controller('RoleCtrl', function ($scope, $modal, $location, Roles, angularPopupBoxes) {
	$scope.searchParams = $location.search();
	$scope.searchParams.hash = 0;
	$scope.page = 1;

	// Search form submitted or page changed
	$scope.search = function() {
		$scope.searchParams.hash++;
		$scope.searchParams.page = $scope.page - 1;

		$location.search($scope.searchParams);

		// Load list on page loaded
		Roles.getList($scope.searchParams).then(function(roles) {
			$scope.roles = roles;
			$scope.page = roles.meta.number + 1;
		});
	};

	$scope.search();

	// ID clicked, open popup form dialog
	$scope.openForm = function(role) {
		var modal = $modal.open({
			templateUrl: 'role.form.html',
			controller: 'RoleFormCtrl',
			size: 'lg',
			resolve: {
				role: function() {
					return role;
				}
			}
		});

		modal.result.then(function(r) {
			$scope.searchParams.q = r.name;
			$scope.search();
		});
	};

	// Open popup confirmation and delete user if user choose yes
	$scope.remove = function(role) {
		angularPopupBoxes.confirm('Are you sure want to delete this data?').result.then(function() {
			role.remove().then(function() {
				$scope.search();
			});
		});
	};
}).controller('RoleFormCtrl', function($scope, $modalInstance, $validation, Roles, role) {
	if (role) {
		$scope.role = role;
	}

	var success = function(r) {
		$modalInstance.close(r);
	};

	var error = function() {
		$scope.error = true;
	};

	$scope.submit = function(role, form) {
		$validation.validate(form).success(function() {
			$scope.error = false;

			if (role.id) {
				role.put().then(success, error);
			} else {
				Roles.post(role).then(success, error);
			}
		});
	};
});
