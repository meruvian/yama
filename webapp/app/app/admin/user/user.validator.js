(function() {
	'use strict';

	angular.module('yamaApp').run(userValidator);

	function userValidator($rootScope, $translate, validationSchema) {
		$rootScope.$on('$translateChangeSuccess', validate);

		function validate() {
			validationSchema.set('user', {
				confpasswd: {
					'validations': 'match=[name="passwd"]',
					'validate-on': 'blur',
					'messages': {
						'match': {
							'error': $translate.instant('admin.user.validation.confirm_passwd_match'),
							'success': 'Ok'
						}
					}
				},
				email: {
					'validations': 'required, email, userexist',
					'validate-on': 'blur',
					'messages': {
						'email': {
							'error': $translate.instant('admin.user.validation.email_valid'),
							'success': 'Ok'
						},
						'required': {
							'error': $translate.instant('admin.user.validation.email_required'),
							'success': 'Ok'
						},
						'userexist': {
							'error': $translate.instant('admin.user.validation.email_exist'),
							'success': 'Ok'
						}
					}
				},
				firstname: {
					'validations': 'required',
					'validate-on': 'blur',
					'messages': {
						'required': {
							'error': $translate.instant('admin.user.validation.firstname_required'),
							'success': 'Ok'
						}
					}
				},
				lastname: {
					'validations': 'required',
					'validate-on': 'blur',
					'messages': {
						'required': {
							'error': $translate.instant('admin.user.validation.lastname_required'),
							'success': 'Ok'
						}
					}
				},
				passwd: {
					'validations': 'required, minlength=6',
					'validate-on': 'blur',
					'messages': {
						'minlength': {
							'error': $translate.instant('admin.user.validation.passwd_length'),
							'success': 'Ok'
						},
						'required': {
							'error': $translate.instant('admin.user.validation.passwd_required'),
							'success': 'Ok'
						}
					}
				},
				username: {
					'validations': 'required, minlength=6, userexist, alphanumeric',
					'validate-on': 'blur',
					'messages': {
						'alphanumeric': {
							'error': $translate.instant('admin.user.validation.username_alphanumeric'),
							'success': 'Ok'
						},
						'minlength': {
							'error': $translate.instant('admin.user.validation.username_length'),
							'success': 'Ok'
						},
						'required': {
							'error': $translate.instant('admin.user.validation.username_required'),
							'success': 'Ok'
						},
						'userexist': {
							'error': $translate.instant('admin.user.validation.username_exist'),
							'success': 'Ok'
						}
					}
				},


			});

			validationSchema.set('passwd', {
				confpasswd: {
					'validations': 'match=[name="passwd"]',
					'validate-on': 'blur',
					'messages': {
						'match': {
							'error': $translate.instant('admin.user.validation.confirm_passwd_match'),
							'success': 'Ok'
						}
					}
				},
				passwd: {
					'validations': 'required, minlength=6',
					'validate-on': 'blur',
					'messages': {
						'minlength': {
							'error': $translate.instant('admin.user.validation.passwd_length'),
							'success': 'Ok'
						},
						'required': {
							'error': $translate.instant('admin.user.validation.passwd_required'),
							'success': 'Ok'
						}
					}
				}
			});
		}
	}
})();
