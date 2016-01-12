(function() {
	'use strict';

	angular.module('yamaApp').config(validationConfig);

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
			}
		});

		$validationProvider.setErrorHTML(function (msg) {
			return '<p class=\"text-danger\">' + msg + '</p>';
		});

		$validationProvider.setSuccessHTML(function (msg) {
			return '<p class=\"text-success\">' + msg + '</p>';
		});
	}
})();
