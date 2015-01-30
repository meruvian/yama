'use strict';

/**
 * @ngdoc function
 * @name yamaApp.controller:ApplicationCtrl
 * @description
 * # ApplicationCtrl
 * Controller of the yamaApp
 */
angular.module('yamaApp').controller('ApplicationCtrl', function ($scope, $modal, $location, Applications, angularPopupBoxes) {
	$scope.searchParams = $location.search();

	// Load list on page loaded
	Applications.getList($scope.searchParams).then(function(applications) {
		$scope.applications = applications;
		$scope.page = applications.meta.number + 1;
	});

	// Search form submitted or page changed
	$scope.search = function(p) {
		p.cache = p.cache || 0;
		p.cache++;
		p.page = $scope.page - 1;

		$location.search(p);
	};

	// ID clicked, open popup form dialog
	$scope.openForm = function(application, changeSecret) {
		var modal = $modal.open({
			templateUrl: 'applicationForm.html',
			controller: 'ApplicationFormCtrl',
			size: 'lg',
			resolve: {
				application: function() {
					return application;
				},
				changeSecret: function() {
					return changeSecret;
				}
			}
		});

		modal.result.then(function() {
			$scope.search($scope.searchParams);
		});
	};

	// Open popup confirmation and delete user if user choose yes
	$scope.remove = function(application) {
		angularPopupBoxes.confirm('Are you sure want to delete this data?').result.then(function() {
			application.remove().then(function() {
				$scope.search($scope.searchParams);
			});
		});
	};
}).controller('ApplicationFormCtrl', function($scope, $modalInstance, Applications, application, changeSecret) {
	$scope.changeSecret = changeSecret || false;

	if (application) {
		application.redirectUri = application.registeredRedirectUris[0];
		$scope.application = application;
	}

	var success = function() {
		$modalInstance.close();
	};

	var error = function() {
		$scope.error = true;
	};

	$scope.submit = function(application) {
		application.registeredRedirectUris = [];
		application.registeredRedirectUris.push(application.redirectUri);

		console.log(application);

		$scope.error = false;

		if (application.id) {
			application.put().then(success, error);
		} else {
			Applications.post(application).then(success, error);
		}
	};

	$scope.generateSecret = function(application) {
		application.post('secret').then(function(a) {
			$scope.application.secret = a.secret;
		}, error);
	};
});
