(function() {
	'use strict';

	angular.module('yamaApp').run(roleValidator);

	function roleValidator($rootScope, $translate, validationSchema) {
		$rootScope.$on('$translateChangeSuccess', validate);

		function validate() {
			validationSchema.set('role', {
				name: {
					'validations': 'required, minlength=4',
					'validate-on': 'blur',
					'messages': {
						'required': {
							'error': $translate.instant('admin.role.validation.name_required'),
							'success': 'Ok'
						},
						'minlength': {
							'error': $translate.instant('admin.role.validation.name_length'),
							'success': 'Ok'
						}
					}
				}
			});
		}
	}
})();
