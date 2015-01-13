'use strict';

describe('Service: DefaultRepository', function () {

  // load the service's module
  beforeEach(module('yamaAppApp'));

  // instantiate service
  var DefaultRepository;
  beforeEach(inject(function (_DefaultRepository_) {
    DefaultRepository = _DefaultRepository_;
  }));

  it('should do something', function () {
    expect(!!DefaultRepository).toBe(true);
  });

});
