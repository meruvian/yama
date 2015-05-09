'use strict';

angular.module('yamaApp').config(function ($stateProvider) {
	$stateProvider.state('backend.dashboard', {
		url: '/dashboard',
		templateUrl: 'backend/admin/dashboard/dashboard.html',
		controller: 'DashboardCtrl'
	});
});
