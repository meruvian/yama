(function() {
	'use strict';

	angular.module('yamaApp').config(oauthConfig);

	function oauthConfig(YamaOAuthProvider) {
		YamaOAuthProvider.configure({
			scope: 'read write',
			clientId: 'yama',
			redirectUri: '/'
		});
	}
})();
