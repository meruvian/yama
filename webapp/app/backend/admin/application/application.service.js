'use strict';

angular.module('yamaApp').factory('Applications', function (Restangular) {
	return Restangular.service('applications');
});
