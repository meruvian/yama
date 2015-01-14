'use strict';

/**
 * @ngdoc service
 * @name yamaApp.Monitoring
 * @description
 * # Monitoring
 * Factory in the yamaApp.
 */
angular.module('yamaApp').factory('Monitoring', function (Restangular) {
	return {
		metrics: Restangular.service('metrics')
	};
});
