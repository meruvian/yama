'use strict';

angular.module('yamaApp').config(function ($stateProvider) {
	$stateProvider.state('login', {
		url: '/login',
		controller: 'LoginCtrl'
	});

	$stateProvider.state('logout', {
		url: '/logout',
		controller: 'LogoutCtrl'
	});
});
