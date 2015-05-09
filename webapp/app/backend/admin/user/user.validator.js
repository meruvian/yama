'use strict';

angular.module('yamaApp').config(function(validationSchemaProvider) {
	var userValidator = {
		username: {
			'validations': 'required, minlength=6',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'Username cannot be blank',
					'success': 'Ok'
				},
				'minlength': {
					'error': 'Must be longer than 5 character',
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
		confpasswd: {
			'validations': 'match=[name="passwd"]',
			'validate-on': 'blur',
			'messages': {
				'match': {
					'error': 'Password and confirm password did not match',
					'success': 'Password match'
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
		email: {
			'validations': 'required, email',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'First name cannot be blank',
					'success': 'Ok'
				},
				'email': {
					'error': 'Not a valid email address',
					'success': 'Ok'
				}
			}
		}
	};

	var passwdValidator = {
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
		confpasswd: {
			'validations': 'match=[name="passwd"]',
			'validate-on': 'blur',
			'messages': {
				'match': {
					'error': 'Password and confirm password did not match',
					'success': 'Password match'
				}
			}
		}
	};

	validationSchemaProvider.set('user', userValidator);
	validationSchemaProvider.set('passwd', passwdValidator);
});
