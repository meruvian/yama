'use strict';

angular.module('yamaApp').controller('MainCtrl', function ($state) {
	$state.go('backend.dashboard');
});
