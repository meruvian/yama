'use strict';

angular.module('yamaApp', [
	'angular-loading-bar',
	'restangular',
	'validation',
	'validation.rule',
	'validation.schema'
]).config(function(RestangularProvider) {
	RestangularProvider.setBaseUrl('/api');
	RestangularProvider.setDefaultHttpFields({cache: false});

	RestangularProvider.addResponseInterceptor(function(data, operation) {
		var extractedData;

		if (operation === 'getList' && angular.isObject(data)) {
			extractedData = angular.copy(data.content, extractedData);
			delete data.content;
			extractedData.meta = data;
		} else {
			extractedData = data;
		}

		return extractedData;
	});
}).config(function($validationProvider) {
	$validationProvider.showSuccessMessage = false;

	$validationProvider.invalidCallback = function(element) {
		$(element).parents('.form-group:first').addClass('has-error');
	};

	$validationProvider.validCallback = function(element) {
		$(element).parents('.form-group:first').removeClass('has-error');
	};

	$validationProvider.setErrorHTML(function (msg) {
		return '<p class=\"text-danger\">' + msg + '</p>';
	});

	$validationProvider.setSuccessHTML(function (msg) {
		return '<p class=\"text-success\">' + msg + '</p>';
	});
}).run(function($validation, Registers) {
	$validation.setExpression({
		match: function (value, scope, element, attrs, param) {
			return value === $(param).val();
		},
		userexist: function (value) {
			return Registers.one().get({u: value}).then(function(user) {
				return !user;
			}, function() {
				return true;
			});
		},
		alphanumeric: /^[a-z0-9]+$/i
	});
});
