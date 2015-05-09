'use strict';

angular.module('yamaApp').config(function ($stateProvider) {
	$stateProvider.state('login', {
		url: '/login',
		templateUrl: 'frontend/login/login.html',
		controller: 'LoginCtrl'
	});

	$stateProvider.state('logout', {
		url: '/logout',
		controller: 'LogoutCtrl'
	});
});
