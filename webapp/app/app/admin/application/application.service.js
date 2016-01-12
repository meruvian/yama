(function() {
	'use strict';

	angular.module('yamaApp').factory('RestApplicationService', restApplicationService);

	function restApplicationService(Restangular) {
		return Restangular.service('applications');
	}
})();
