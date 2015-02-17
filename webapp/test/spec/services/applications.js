'use strict';

describe('Service: Applications', function () {

  // load the service's module
  beforeEach(module('yamaApp'));

  // instantiate service
  var Applications;
  beforeEach(inject(function (_Applications_) {
    Applications = _Applications_;
  }));

  it('should do something', function () {
    expect(!!Applications).toBe(true);
  });

});
