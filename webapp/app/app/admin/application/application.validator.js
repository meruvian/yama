(function() {
	'use strict';

	angular.module('yamaApp').run(applicationValidator);

	function applicationValidator($rootScope, $translate, validationSchema) {
		$rootScope.$on('$translateChangeSuccess', validate);

		function validate() {
			validationSchema.set('application', {
				name: {
					'validations': 'required',
					'validate-on': 'blur',
					'messages': {
						'required': {
							'error': $translate.instant('admin.application.validation.name_required'),
							'success': 'Ok'
						}
					}
				},
				redirect: {
					'validations': 'url, required',
					'validate-on': 'blur',
					'messages': {
						'required': {
							'error': $translate.instant('admin.application.validation.redirect_required'),
							'success': 'Ok'
						},
						'url': {
							'error': $translate.instant('admin.application.validation.redirect_url'),
							'success': 'Ok'
						}
					}
				},
				website: {
					'validations': 'url, required',
					'validate-on': 'blur',
					'messages': {
						'required': {
							'error': $translate.instant('admin.application.validation.site_required'),
							'success': 'Ok'
						},
						'url': {
							'error': $translate.instant('admin.application.validation.site_url'),
							'success': 'Ok'
						}
					}
				}
			});
		}
	}
})();
