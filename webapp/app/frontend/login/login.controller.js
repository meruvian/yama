'use strict';

angular.module('yamaApp').controller('LoginCtrl', function () {
	// do nothing
}).controller('LogoutCtrl', function($state, $http, YamaOAuth) {
	$http.get('/auth/logout').success(function() {
		YamaOAuth.logout();
	});
});
