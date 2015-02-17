'use strict';

describe('Service: Roles', function () {

  // load the service's module
  beforeEach(module('yamaApp'));

  // instantiate service
  var Roles;
  beforeEach(inject(function (_Roles_) {
    Roles = _Roles_;
  }));

  it('should do something', function () {
    expect(!!Roles).toBe(true);
  });

});
