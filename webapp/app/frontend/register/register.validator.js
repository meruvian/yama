'use strict';

angular.module('yamaApp').config(function(validationSchemaProvider) {
	var registerValidator = {
		username: {
			'validations': 'required, minlength=6, userexist, alphanumeric',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'Username cannot be blank',
					'success': 'Ok'
				},
				'minlength': {
					'error': 'Must be longer than 5 character',
					'success': 'Ok'
				},
				'userexist': {
					'error': 'Username already exist',
					'success': 'Ok'
				},
				'alphanumeric': {
					'error': 'Only alphanumerics are allowed',
					'success': 'Ok'
				}
			}
		},
		passwd: {
			'validations': 'required, minlength=6',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'Password cannot be blank',
					'success': 'Ok'
				},
				'minlength': {
					'error': 'Must be longer than 5 character',
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
			'validations': 'required, email, userexist',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'Email cannot be blank',
					'success': 'Ok'
				},
				'email': {
					'error': 'Not a valid email address',
					'success': 'Ok'
				},
				'userexist': {
					'error': 'Email address already exist',
					'success': 'Ok'
				}
			}
		}
	};

	validationSchemaProvider.set('register', registerValidator);
});
