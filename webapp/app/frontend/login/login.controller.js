'use strict';

angular.module('yamaApp').controller('LoginCtrl', function ($scope, $state, $rootScope, $location, OAuth, Users) {
	$scope.isAuthenticated = true;

	$scope.go = function() {
		if ($rootScope.$state.toState) {
			var toState = $rootScope.$state.toState;
			delete $rootScope.$state.toState;

			$state.go(toState.name, toState.params);
		} else {
			$state.go('main');
		}
	};

	if (OAuth.isAuthenticated()) {
		$scope.go();
	} else {
		Users.one('me').get().then(function() {
			OAuth.getAccessTokenFromCode();
		});
	}

	$scope.login = function(user) {
		$scope.isAuthenticated = true;

		OAuth.getAccessToken(user).then(function() {
			$scope.go();
		}, function() {
			$scope.isAuthenticated = false;
		});
	};
}).controller('LogoutCtrl', function($state, $http, OAuthToken) {
	$http.get('logout').success(function() {
		OAuthToken.removeToken();
		$state.go('main');
	});
});
