(function() {
	'use strict';

	angular.module('yamaApp').config(userValidator);

	function userValidator(validationSchemaProvider) {
		validationSchemaProvider.set('user', {
			confpasswd: {
				'validations': 'match=[name="passwd"]',
				'validate-on': 'blur',
				'messages': {
					'match': {
						'error': 'Password and confirm password did not match',
						'success': 'Ok'
					}
				}
			},
			email: {
				'validations': 'required, email, userexist',
				'validate-on': 'blur',
				'messages': {
					'email': {
						'error': 'Not a valid email address',
						'success': 'Ok'
					},
					'required': {
						'error': 'First name cannot be blank',
						'success': 'Ok'
					},
					'userexist': {
						'error': 'Email address already exist',
						'success': 'Ok'
					}
				}
			},
			firstname: {
				'validations': 'required',
				'validate-on': 'blur',
				'messages': {
					'required': {
						'error': 'First name cannot be blank',
						'success': 'Ok'
					}
				}
			},
			lastname: {
				'validations': 'required',
				'validate-on': 'blur',
				'messages': {
					'required': {
						'error': 'Last name cannot be blank',
						'success': 'Ok'
					}
				}
			},
			passwd: {
				'validations': 'required',
				'validate-on': 'blur',
				'messages': {
					'required': {
						'error': 'Password cannot be blank',
						'success': 'Ok'
					}
				}
			},
			username: {
				'validations': 'required, minlength=6, userexist, alphanumeric',
				'validate-on': 'blur',
				'messages': {
					'alphanumeric': {
						'error': 'Only alphanumeric are allowed',
						'success': 'Ok'
					},
					'minlength': {
						'error': 'Must be longer than 5 character',
						'success': 'Ok'
					},
					'required': {
						'error': 'Username cannot be blank',
						'success': 'Ok'
					},
					'userexist': {
						'error': 'Username already exist',
						'success': 'Ok'
					}
				}
			},


		});

		validationSchemaProvider.set('passwd', {
			confpasswd: {
				'validations': 'match=[name="passwd"]',
				'validate-on': 'blur',
				'messages': {
					'match': {
						'error': 'Password and confirm password did not match',
						'success': 'Ok'
					}
				}
			},
			passwd: {
				'validations': 'required, minlength=6',
				'validate-on': 'blur',
				'messages': {
					'minlength': {
						'error': 'Must be longer than 5 character',
						'success': 'Ok'
					},
					'required': {
						'error': 'Password cannot be blank',
						'success': 'Ok'
					}
				}
			}
		});
	}
})();
