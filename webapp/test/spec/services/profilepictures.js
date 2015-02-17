'use strict';

describe('Service: ProfilePictures', function () {

  // load the service's module
  beforeEach(module('yamaApp'));

  // instantiate service
  var ProfilePictures;
  beforeEach(inject(function (_ProfilePictures_) {
    ProfilePictures = _ProfilePictures_;
  }));

  it('should do something', function () {
    expect(!!ProfilePictures).toBe(true);
  });

});
