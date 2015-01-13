'use strict';

/**
 * @ngdoc overview
 * @name yamaApp
 * @description
 * # yamaApp
 *
 * Main module of the application.
 */
angular.module('yamaApp', [
	'ngAnimate',
	'ngCookies',
	'ngResource',
	'ngRoute',
	'ngSanitize',
	'ngTouch',
	'ui.bootstrap',
	'oauth',
	'angular-loading-bar',
	'jcs-autoValidate',
	'restangular',
	'angularPopupBoxes'
]).config(function ($routeProvider, $locationProvider) {
	$locationProvider.html5Mode(false).hashPrefix('!');
	$routeProvider
	.when('/', {
		templateUrl: 'views/main.html',
		controller: 'MainCtrl'
	})
	.when('/about', {
		templateUrl: 'views/about.html',
		controller: 'AboutCtrl'
	})
	.when('/admin/roles', {
		templateUrl: 'views/role.html',
		controller: 'RoleCtrl'
	})
	.when('/admin/users', {
		templateUrl: 'views/user.html',
		controller: 'UserCtrl'
	})
	.when('/myprofile', {
		templateUrl: 'views/myprofile.html',
		controller: 'MyprofileCtrl'
	})
	.otherwise({
		redirectTo: '/'
	});
}).config(function($httpProvider, RestangularProvider) {
	$httpProvider.interceptors.push('Oauth2RequestInterceptor');

	RestangularProvider.setBaseUrl('http://localhost:8080');

	RestangularProvider.addResponseInterceptor(function(data, operation) {
		var extractedData;

		if (operation === 'getList' && angular.isObject(data)) {
			extractedData = angular.copy(data.content, extractedData);
			delete data.content;
			extractedData.meta = data;
		} else {
			extractedData = data;
		}

		return extractedData;
	});
}).run(function (bootstrap3ElementModifier) {
	bootstrap3ElementModifier.enableValidationStateIcons(false);
});
