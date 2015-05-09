'use strict';

angular.module('yamaApp').config(function ($stateProvider) {
	$stateProvider.state('main', {
		url: '/',
		controller: 'MainCtrl'
	});
});
