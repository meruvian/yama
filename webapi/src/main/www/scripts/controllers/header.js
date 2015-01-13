'use strict';

/**
 * @ngdoc function
 * @name yamaApp.controller:HeaderCtrl
 * @description
 * # HeaderCtrl
 * Controller of the yamaApp
 */
angular.module('yamaApp').controller('HeaderCtrl', function ($scope, Endpoint, AccessToken, Users) {
	$scope.$on('oauth:loggedOut', function() {
		Endpoint.redirect();
	});

	$scope.$on('oauth:authorized', function() {
		Users.one('me').get().then(function(user) {
			$scope.currentUser = user;
		});
	});

	$scope.logout = function() {
		AccessToken.destroy($scope);
	};
});
