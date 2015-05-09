'use strict';

angular.module('yamaApp').factory('Dashboards', function (Restangular) {
	return {
		metrics: Restangular.service('actuator/metrics')
	};
});
