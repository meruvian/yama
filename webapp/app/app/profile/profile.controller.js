(function() {
	'use strict';

	angular.module('yamaApp').controller('ProfileCtrl', profileController);

	function profileController($state) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.menus = [
			{ menu: 'Basic', icon: 'user', ref: 'app.profile.basic' },
			{ menu: 'Password', icon: 'key', ref: 'app.profile.passwd' },
			{ menu: 'Profile Picture', icon: 'camera-retro', ref: 'app.profile.picture' }
		];

		ctrl.state = $state;

		if (ctrl.state.current.name === 'app.profile') {
			ctrl.state.go(ctrl.menus[0].ref);
		}
	}
})();
