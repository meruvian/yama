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
	'yama-config',
	'ngAnimate',
	'ngCookies',
	'ngResource',
	'ngRoute',
	'ngSanitize',
	'ngTouch',
	'ui.bootstrap',
	'ui.select',
	'oauth',
	'angular-loading-bar',
	'jcs-autoValidate',
	'restangular',
	'angularPopupBoxes',
	'angularFileUpload'
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
	.when('/applications', {
		templateUrl: 'views/application.html',
		controller: 'ApplicationCtrl'
	})
	.when('/my/profile', {
		templateUrl: 'views/myprofile.html',
		controller: 'MyProfileCtrl'
	})
	.otherwise({
		redirectTo: '/'
	});
}).config(function($httpProvider, RestangularProvider, uiSelectConfig) {
	// Http and Restangular
	$httpProvider.interceptors.push('Oauth2RequestInterceptor');

	RestangularProvider.setBaseUrl('/api');
	RestangularProvider.setDefaultHttpFields({cache: true});

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

	// UI-Select
	uiSelectConfig.theme = 'bootstrap';
	uiSelectConfig.resetSearchInput = true;
}).run(function (bootstrap3ElementModifier) {
	bootstrap3ElementModifier.enableValidationStateIcons(false);
}).run(function($rootScope, $http, AccessToken, Endpoint, Users, ProfilePictures, oauthConfig) {
	Endpoint.set(oauthConfig);
	AccessToken.set(oauthConfig);

	$rootScope.$on('oauth:loggedOut', function() {
		Endpoint.redirect();
	});

	$rootScope.$on('oauth:authorized', function() {
		Users.one('me').get().then(function(user) {
			$rootScope.currentUser = user;
		});

		ProfilePictures.reloadPhoto();
	});

	$rootScope.logout = function() {
		$http.get('/logout').success(function() {
			AccessToken.destroy();

			setTimeout(function() {
				$rootScope.$broadcast('oauth:logout');
				$rootScope.$broadcast('oauth:loggedOut');
			}, 500);
		});
	};
});
