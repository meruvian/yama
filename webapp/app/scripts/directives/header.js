'use strict';

/**
 * @ngdoc directive
 * @name yamaApp.directive:header
 * @description
 * # header
 */
angular.module('yamaApp').directive('header', function ($rootScope) {
	return {
		restrict: 'E',
		link: function postLink(scope, element, attrs) {
			$rootScope.header = attrs.header;
			$rootScope.subheader = attrs.subheader;
		}
	};
});
