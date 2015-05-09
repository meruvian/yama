'use strict';

angular.module('yamaApp').config(function ($stateProvider) {
	$stateProvider.state('backend', {
		url: '/backend',
		templateUrl: 'backend/backend.html',
		controller: 'BackendCtrl'
	});
});
