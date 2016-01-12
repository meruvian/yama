(function() {
	'use strict';

	angular.module('yamaApp').config(selectConfig);

	function selectConfig(uiSelectConfig) {
		uiSelectConfig.theme = 'bootstrap';
		uiSelectConfig.resetSearchInput = true;
	}
})();
