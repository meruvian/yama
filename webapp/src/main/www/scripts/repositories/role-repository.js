'use strict';

angular.module('yamaApp')
  .factory('RoleRepository', function ($injector, RoleModel) {
    var BaseRepository = $injector.get('BaseRepository');
    return new BaseRepository({name: 'RoleRepository', model: RoleModel});
  });