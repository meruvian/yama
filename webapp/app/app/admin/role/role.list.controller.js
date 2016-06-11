(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminRoleListCtrl', roleListController);

	function roleListController($location, $uibModal, Dialog, RestRoleService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.openForm = openRoleForm;
		ctrl.page = 1;
		ctrl.remove = removeRole;
		ctrl.searchParams = $location.search();
		ctrl.searchParams.hash = 0;
		ctrl.search = searchRoles;
		ctrl.search();

		function onRolesLoaded(roles) {
			ctrl.roles = roles;
			ctrl.page = roles.meta.number + 1;
		}

		function openRoleForm(role, changeSecret) {
			var modal = $uibModal.open({
				templateUrl: 'app/admin/role/role.form.html',
				controller: 'AdminRoleFormCtrl as ctrl',
				size: 'md',
				resolve: {
					role: function() { return role; },
					changeSecret: function() { return changeSecret; }
				}
			});

			modal.result.then(success);

			function success(result) {
				ctrl.searchParams.q = result.name;
				ctrl.search();
			}
		}

		function removeRole(role) {
			Dialog.confirm('Are you sure want to delete this data?')
					.result.then(remove);

			function remove() {
				role.remove().then(ctrl.search);
			}
		}

		function searchRoles() {
			ctrl.searchParams.hash++;
			ctrl.searchParams.page = ctrl.page - 1;

			$location.search(ctrl.searchParams);

			RestRoleService.getList(ctrl.searchParams).then(onRolesLoaded);
		}
	}
})();
