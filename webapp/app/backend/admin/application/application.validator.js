'use strict';

angular.module('yamaApp').config(function(validationSchemaProvider) {
	var applicationValidator = {
		name: {
			'validations': 'required',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'Name cannot be blank',
					'success': 'Ok'
				}
			}
		},
		website: {
			'validations': 'url, required',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'Site cannot be blank',
					'success': 'Ok'
				},
				'url': {
					'error': 'Not a valid URL',
					'success': 'Ok'
				}
			}
		},
		redirect: {
			'validations': 'url, required',
			'validate-on': 'blur',
			'messages': {
				'required': {
					'error': 'Site cannot be blank',
					'success': 'Ok'
				},
				'url': {
					'error': 'Not a valid URL',
					'success': 'Ok'
				}
			}
		}
	};

	validationSchemaProvider.set('application', applicationValidator);
});
