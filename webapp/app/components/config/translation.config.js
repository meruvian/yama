(function() {
	'use strict';

	angular.module('yamaApp').config(translationConfig);

	function translationConfig($translateProvider) {
		$translateProvider.useStaticFilesLoader({
			prefix: 'i18n/',
			suffix: '.json'
		})
		.preferredLanguage('en')
		.useSanitizeValueStrategy('escapeParameters');
	}
})();
