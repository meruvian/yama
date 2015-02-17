'use strict';

describe('Controller: MyprofileCtrl', function () {

  // load the controller's module
  beforeEach(module('yamaApp'));

  var MyprofileCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MyprofileCtrl = $controller('MyprofileCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
