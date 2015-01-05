angular.module('yamaApp')
  .run(function (Config, $httpBackend, $log, APIBaseUrl, regexEscape, guid, mockRepository) {
    if (!Config.API.useMocks) return;

    var collectionUrl = APIBaseUrl + 'roles';
    var IdRegExp = /[\d\w-_]+$/.toString().slice(1, -1);

    var roles = collectionUrl;
    var RoleById = new RegExp(regexEscape(collectionUrl + '/') + IdRegExp);

    $log.log('***************************************************************************************************************');
    $log.log('Overriding all calls to `' + collectionUrl + '` with mocks defined in *dev/role-mocks.js*');
    $log.log('***************************************************************************************************************');

    var seed = [
      {id: guid(), text: 'AngularJS'},
      {id: guid(), text: 'Karma'},
      {id: guid(), text: 'Yeoman'},
      {id: guid(), text: 'Generator-angular-xl'}
    ];

    var RoleRepo = mockRepository.create();

    angular.forEach(seed, function (item, key) {
        RoleRepo.insert(item.id, item);
    });

    //GET roles/
    $httpBackend.whenGET(roles).respond(function (method, url, data, headers) {
      $log.debug('Intercepted GET to `' + url + '`', data);
      return [200, RoleRepo.getAll(), {/*headers*/}];
    });

    //POST roles/
    $httpBackend.whenPOST(roles).respond(function (method, url, data, headers) {
      $log.debug('Intercepted POST to `' + url + '`', data);

      var Role = RoleRepo.push(angular.fromJson(data));

      return [200, Role, {/*headers*/}];
    });

    //GET roles/<id>
    $httpBackend.whenGET(RoleById).respond(function (method, url, data, headers) {
      $log.debug('Intercepted GET to `' + url + '`');
      var id = url.match(new RegExp(IdRegExp))[0];
      var item = RoleRepo.getById(id);
      return [item ? 200 : 404, item || null, {/*headers*/}];
    });

    //PUT roles/<id>
    $httpBackend.whenPUT(RoleById).respond(function (method, url, data, headers) {
      $log.debug('Intercepted PUT to `' + url + '`');
      var id = url.match(new RegExp(IdRegExp))[0];

      if (!RoleRepo.getById(id)) {
        return [404, {} , {/*headers*/}];
      }

      var Role = RoleRepo.insert(id, angular.fromJson(data));

      return [200, Role, {/*headers*/}];
    });

    //DELETE roles/<id>
    $httpBackend.whenDELETE(new RegExp(regexEscape(collectionUrl + '/') + IdRegExp)).respond(function (method, url, data, headers) {
      $log.debug('Intercepted DELETE to `' + url + '`');
      var id = url.match(new RegExp(IdRegExp))[0];

      var Role = RoleRepo.getById(id);
      if (!Role) {
        return [404, {} , {/*headers*/}];
      }
      RoleRepo.remove(Role.id);

      return [200, Role , {/*headers*/}];
    });

  });


