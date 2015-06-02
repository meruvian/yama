'use strict';

angular.module('yamaOauth').factory('YamaOauthInterceptor', function($rootScope, $q, AccessToken) {
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
}).config(function($httpProvider) {
	$httpProvider.interceptors.push('YamaOauthInterceptor');
});
