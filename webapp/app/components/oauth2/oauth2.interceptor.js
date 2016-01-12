(function() {
	'use strict';

	angular.module('yamaOauth')
		.config(yamaOauthConfig)
		.factory('YamaOauthInterceptor', yamaOauthInterceptor);

	function yamaOauthConfig($httpProvider) {
		$httpProvider.interceptors.push('YamaOauthInterceptor');
	}

	function yamaOauthInterceptor($rootScope, $q, AccessToken) {
		return {
			request: function($config) {
				if (AccessToken.get()) {
					$config.headers.Authorization = 'Bearer ' + AccessToken.get().access_token;
				}
				return $config;
			},
			responseError: function(rejection) {
				if (401 === rejection.status) {
					$rootScope.$broadcast('oauth:unauthorized', rejection);
				}

				return $q.reject(rejection);
			}
		};
	}
})();
