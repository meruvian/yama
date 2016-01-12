(function() {
	'use strict';

	angular.module('yamaApp').controller('AppCtrl', appController);

	function appController($state, AppService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.menus = [
			{ menu: 'Admin', icon: 'gears', ref: 'app.admin' }
		];
		ctrl.logout = logout;

		if ($state.current.name === 'app.root' || $state.current.name === 'app') {
			$state.go(ctrl.menus[0].ref);
		}

		function logout() {
			AppService.logout();
		}
	}
})();
