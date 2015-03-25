'use strict';

/**
 * @ngdoc service
 * @name yamaApp.Applications
 * @description
 * # Applications
 * Factory in the yamaApp.
 */
angular.module('yamaApp').factory('Applications', function (Restangular) {
	return Restangular.service('api/applications');
});
