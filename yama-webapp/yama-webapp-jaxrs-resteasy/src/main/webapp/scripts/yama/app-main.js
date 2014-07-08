var app = angular.module('yama', ['ngRoute']);

app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
	$locationProvider.html5Mode(true).hashPrefix('!');;
	
	$routeProvider
    	.when('/dashboard', {templateUrl: '/register'})
    	.when('/test', {templateUrl: '/dashboard/test.htm'});
	
}]);