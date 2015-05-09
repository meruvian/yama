'use strict';

angular.module('yamaApp').config(function ($stateProvider) {
	$stateProvider.state('backend.user', {
		url: '/admin/user',
		templateUrl: 'backend/admin/user/user.html',
		controller: 'UserCtrl'
	});
});
