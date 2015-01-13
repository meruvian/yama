'use strict';

describe('Service: APIBaseUrl', function () {

  // load the service's module
  beforeEach(module('yamaAppApp'));

  // instantiate service
  var APIBaseUrl;
  beforeEach(inject(function (_APIBaseUrl_) {
    APIBaseUrl = _APIBaseUrl_;
  }));

  it('should do something', function () {
    expect(!!APIBaseUrl).toBe(true);
  });

});
