'use strict';

describe('Service: Users', function () {

  // load the service's module
  beforeEach(module('yamaApp'));

  // instantiate service
  var Users;
  beforeEach(inject(function (_Users_) {
    Users = _Users_;
  }));

  it('should do something', function () {
    expect(!!Users).toBe(true);
  });

});
