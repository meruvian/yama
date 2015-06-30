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
	'angular-loading-bar',
	'restangular',
	'angularPopupBoxes',
	'angularFileUpload',
	'validation',
	'validation.rule',
	'validation.schema',
	'yamaOauth'
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
}).config(function(YamaOAuthProvider) {
	YamaOAuthProvider.configure({
		scope: 'read write',
		clientId: 'yama',
		redirectUri: '/'
	});
}).config(function($validationProvider) {
	$validationProvider.showSuccessMessage = false;

	$validationProvider.invalidCallback = function(element) {
		$(element).parents('.form-group:first').addClass('has-error');
	};

	$validationProvider.validCallback = function(element) {
		$(element).parents('.form-group:first').removeClass('has-error');
	};

	$validationProvider.setExpression({
		match: function (value, scope, element, attrs, param) {
			return value === $(param).val();
		}
	});

	$validationProvider.setErrorHTML(function (msg) {
		return '<p class=\"text-danger\">' + msg + '</p>';
	});

	$validationProvider.setSuccessHTML(function (msg) {
		return '<p class=\"text-success\">' + msg + '</p>';
	});
}).run(function($rootScope, YamaOAuth, Users, ProfilePictures) {
	$rootScope.$state = {};

	if (!YamaOAuth.isAuthorized()) {
		YamaOAuth.login();
	} else {
		if (!$rootScope.currentUser) {
			Users.one('me').get().then(function(user) {
				$rootScope.currentUser = user;

				ProfilePictures.reloadPhoto();
			});
		}
	}

	$rootScope.$on('oauth:unauthorized', function() {
		YamaOAuth.login();
	});

	$rootScope.$on('oauth:logout', function() {
		YamaOAuth.login();
	});

	$rootScope.$on('$stateChangeStart', function(event, toState, toParams) {
		if (!YamaOAuth.isAuthorized()) {
			event.preventDefault();

			$rootScope.$state.toState = toState;
			$rootScope.$state.toState.params = toParams;

			YamaOAuth.login();
		}
	});
});
