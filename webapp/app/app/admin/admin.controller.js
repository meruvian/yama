(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminCtrl', adminController);

	function adminController($state) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.menus = [
			{ menu: 'Users', icon: 'user', ref: 'app.admin.user' },
			{ menu: 'Roles', icon: 'users', ref: 'app.admin.role' },
			{ menu: 'Applications', icon: 'cube', ref: 'app.admin.application' }
		];
		ctrl.state = $state;

		if (ctrl.state.current.name === 'app.admin') {
			ctrl.state.go(ctrl.menus[0].ref);
		}
	}
})();
