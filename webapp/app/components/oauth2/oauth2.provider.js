'use strict';

angular.module('yamaOauth').provider('YamaOAuth', function() {
	var defaultConfig = {
		clientId: '',
		redirectUri: location.protocol + '//' + location.host,
		site: location.protocol + '//' + location.host,
		authorizePath: '/oauth/authorize',
		tokenPath: '/oauth/token',
		responseType: 'token',
		state: null,
		scope: null,
		storage: 'sessionStorage'
	};

	var config = {};
	var $rootScope, $timeout, Endpoint, AccessToken, Storage;

	var setup = function($injector) {
		$rootScope = $injector.get('$rootScope');
		$timeout = $injector.get('$timeout');
		Endpoint = $injector.get('Endpoint');
		AccessToken = $injector.get('AccessToken');
		Storage = $injector.get('Storage');

		Storage.use(config.storage);
		Endpoint.set(config);
		AccessToken.set(config);

		$rootScope.$on('oauth:expired', function() {
			AccessToken.destroy();
		});
	};

	this.configure = function(params) {
		if (!(params instanceof Object)) {
			throw new TypeError('Config must be object');
		}

		config = angular.extend({}, defaultConfig, params);
	};

	this.login = function() {
		AccessToken.destroy();
		Endpoint.redirect();
	};

	this.logout = function() {
		AccessToken.destroy();
		$timeout(function() {
			$rootScope.$broadcast('oauth:logout');
		}, 500);
	};

	this.isAuthorized =  function() {
		return !(AccessToken.get() === null || AccessToken.expired());
	};

	this.getAccessToken = function() {
		return AccessToken.get();
	};

	this.isExpired = function() {
		return AccessToken.expired();
	};

	this.$get = function($injector) {
		setup($injector);

		return {
			login: this.login,
			logout: this.logout,
			isAuthorized: this.isAuthorized,
			getAccessToken: this.getAccessToken,
			isExpired: this.isExpired
		};
	};
});
