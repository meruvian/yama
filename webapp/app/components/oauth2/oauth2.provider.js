(function() {
	'use strict';

	angular.module('yamaOauth').provider('YamaOAuth', yamaOauthProvider);

	function yamaOauthProvider() {
		var config = {};
		var defaultConfig = {
			authorizePath: '/oauth/authorize',
			clientId: '',
			redirectUri: location.protocol + '//' + location.host,
			responseType: 'token',
			scope: null,
			site: location.protocol + '//' + location.host,
			state: null,
			storage: 'sessionStorage',
			tokenPath: '/oauth/token'
		};
		var $rootScope, $timeout, AccessToken, Endpoint, Storage;
		/* jshint validthis: true */
		var vm = this;
		vm.$get = $get;
		vm.configure = configure;
		vm.getAccessToken = getAccessToken;
		vm.isAuthorized = isAuthorized;
		vm.isExpired = isExpired;
		vm.login = login;
		vm.logout = logout;

		function $get($injector) {
			'ngInject';
			setup($injector);

			return {
				login: this.login,
				logout: this.logout,
				isAuthorized: this.isAuthorized,
				getAccessToken: this.getAccessToken,
				isExpired: this.isExpired
			};
		}

		function configure(params) {
			if (!(params instanceof Object)) {
				throw new TypeError('Config must be object');
			}

			config = angular.extend({}, defaultConfig, params);
		}

		function getAccessToken() {
			return AccessToken.get();
		}

		function isAuthorized() {
			return !(AccessToken.get() === null || AccessToken.expired());
		}

		function isExpired() {
			return AccessToken.expired();
		}

		function login() {
			AccessToken.destroy();
			Endpoint.redirect();
		}

		function logout() {
			AccessToken.destroy();
			$timeout(function() {
				$rootScope.$broadcast('oauth:logout');
			}, 500);
		}

		function setup($injector) {
			$rootScope = $injector.get('$rootScope');
			$timeout = $injector.get('$timeout');
			AccessToken = $injector.get('AccessToken');
			Endpoint = $injector.get('Endpoint');
			Storage = $injector.get('Storage');

			AccessToken.set(config);
			Endpoint.set(config);
			Storage.use(config.storage);

			$rootScope.$on('oauth:expired', function() {
				AccessToken.destroy();
			});
		}
	}
})();
