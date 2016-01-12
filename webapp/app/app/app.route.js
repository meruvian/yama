(function() {
	'use strict';

	angular.module('yamaApp').config(appRoute);

	function appRoute($stateProvider) {
		$stateProvider.state('app', {
			url: '',
			templateUrl: 'app/app.html',
			controller: 'AppCtrl as ctrl'
		}).state('app.root', {
			url: '/'
		});
	}
})();
