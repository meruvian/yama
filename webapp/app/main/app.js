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
	'ngSanitize',
	'ngTouch',
	'ui.bootstrap',
	'ui.select',
	'ui.router',
	'angular-oauth2',
	'angular-loading-bar',
	'restangular',
	'angularPopupBoxes',
	'angularFileUpload',
	'validation',
	'validation.rule',
	'validation.schema'
]).config(function ($locationProvider, $urlRouterProvider) {
	$locationProvider.html5Mode(false).hashPrefix('!');

	$urlRouterProvider.otherwise('/');
}).config(function($httpProvider, RestangularProvider, uiSelectConfig) {
	RestangularProvider.setBaseUrl('/api');
	RestangularProvider.setDefaultHttpFields({cache: false});

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
}).config(function(OAuthProvider) {
	OAuthProvider.configure({
		baseUrl: location.protocol + '//' + location.host,
		clientId: 'yama',
		clientSecret: 'yamameruvianorgsecret',
		authorizePath: '/oauth/authorize',
		grantPath: '/oauth/token',
		revokePath: '/oauth/revoke'
	});
}).config(function($validationProvider) {
	$validationProvider.setExpression({
		match: function (value, scope, element, attrs, param) {
			return value === $(param).val();
		}
	});

	$validationProvider.setErrorHTML(function (msg) {
		return  '<p class=\"text-danger\">' + msg + '</p>';
	});

	$validationProvider.setSuccessHTML(function (msg) {
		return  '<p class=\"text-success\">' + msg + '</p>';
	});
}).run(function($rootScope, $state, OAuth, OAuthToken, Users, ProfilePictures) {
	$rootScope.$state = {};

	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams) {
		$rootScope.$state.current = toState;
		$rootScope.$state.current.params = toParams;

		if (OAuth.isAuthenticated()) {
			if (!$rootScope.currentUser) {
				Users.one('me').get().then(function(user) {
					$rootScope.currentUser = user;

					ProfilePictures.reloadPhoto();
				});
			}
		}
	});

	$rootScope.$on('$stateChangeStart', function(event, toState, toParams) {
		if (!OAuth.isAuthenticated() && 'login' !== toState.name) {
			event.preventDefault();

			$rootScope.$state.toState = toState;
			$rootScope.$state.toState.params = toParams;

			$state.go('login');
		}
	});

	$rootScope.$on('oauth:error', function() {
		if (OAuthToken.getRefreshToken()) {
			OAuth.getRefreshToken();
		} else {
			$state.go('main');
		}

		OAuthToken.removeToken();
	});
});
