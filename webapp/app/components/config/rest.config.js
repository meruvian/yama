(function() {
	'use strict';

	angular.module('yamaApp').config(restConfig);

	function restConfig($httpProvider, RestangularProvider) {
		RestangularProvider.setBaseUrl('/api');
		RestangularProvider.setDefaultHttpFields({cache: false});

		RestangularProvider.addResponseInterceptor(function(data, operation) {
			var extractedData;

			if (operation === 'getList' && angular.isObject(data)) {
				extractedData = angular.copy(data.content, extractedData);
				delete data.content;
				extractedData.meta = data;
			} else {
				extractedData = data;
			}

			return extractedData;
		});
	}
})();
