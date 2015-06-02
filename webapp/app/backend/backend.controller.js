'use strict';

angular.module('yamaApp').controller('BackendCtrl', function ($state) {
	if ('backend' === $state.current.name) {
		$state.transitionTo('backend.summary');
	}
});
