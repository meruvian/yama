(function() {
	'use strict';

	angular.module('yamaApp').config(validationConfig).run(userValidationConfig);

	function validationConfig($validationProvider) {
		$validationProvider.showSuccessMessage = false;

		$validationProvider.invalidCallback = function(element) {
			$(element).parents('.form-group:first').addClass('has-error');
		};

		$validationProvider.validCallback = function(element) {
			$(element).parents('.form-group:first').removeClass('has-error');
		};

		$validationProvider.setExpression({
			match: function (value, scope, element, attrs, param) {
				return value === $(param).val();
			},
			alphanumeric: /^[a-z0-9]+$/i
		});

		$validationProvider.setErrorHTML(function (msg) {
			return '<p class=\"text-danger\">' + msg + '</p>';
		});

		$validationProvider.setSuccessHTML(function (msg) {
			return '<p class=\"text-success\">' + msg + '</p>';
		});
	}

	function userValidationConfig($validation, RestUserService) {
		$validation.setExpression({
			userexist: function(value, scope, element, attrs) {
				if (attrs.exclude) {
					return true;
				}

				return RestUserService.one(value).get().then(function(user) {
					return !user;
				}, function() {
					return true;
				});
			}
		});
	}
})();
