(function() {
	'use strict';

	angular.module('yamaApp').config(applicationValidator);

	function applicationValidator(validationSchemaProvider) {
		validationSchemaProvider.set('application', {
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
			}
		});
	}
})();
