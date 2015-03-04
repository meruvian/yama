'use strict';

/**
 * @ngdoc service
 * @name yamaAppApp.Oauth2RequestInterceptor
 * @description
 * # Oauth2RequestInterceptor
 * Factory in the yamaAppApp.
 */
angular.module('yamaApp').factory('Oauth2RequestInterceptor', function ($rootScope, $q, AccessToken) {
	return {
		request: function($config) {
			if (AccessToken.get()) {
				$config.headers.Authorization = 'Bearer ' + AccessToken.get().access_token;
			}
			return $config;
		},
		responseError: function(rejection) {
			if (rejection.status === 401) {
				AccessToken.destroy($rootScope);
				$rootScope.$broadcast('oauth:logout');
				$rootScope.$broadcast('oauth:loggedOut');
			}

			return $q.reject(rejection);
		}
	};
});
