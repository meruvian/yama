(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminUserListCtrl', userListController);

	function userListController($location, $modal, angularPopupBoxes, RestUserService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.addRole = openAddRoleForm;
		ctrl.changePasswd = openChangeUserPasswdForm;
		ctrl.openForm = openUserForm;
		ctrl.page = 1;
		ctrl.remove = removeUser;
		ctrl.searchParams = $location.search();
		ctrl.searchParams.hash = 0;
		ctrl.search = searchUsers;
		ctrl.search();

		function loadRolesByUser(user) {
			user.one('roles').getList().then(loadRoles);

			function loadRoles(roles) {
				user.roles = roles;
			}
		}

		function onUsersLoaded(users) {
			ctrl.users = users;
			ctrl.page = users.meta.number + 1;

			for (var i = 0; i < users.length; i++) {
				loadRolesByUser(users[i]);
			}
		}

		function openAddRoleForm(user) {
			var modal = $modal.open({
				templateUrl: 'app/admin/user/user.role.html',
				controller: 'AdminUserRoleCtrl as ctrl',
				size: 'md',
				resolve: {
					user: function() { return user; }
				}
			});

			modal.result.then(ctrl.search);
		}

		function openChangeUserPasswdForm(user) {
			var modal = $modal.open({
				templateUrl: 'app/admin/user/user.passwd.html',
				controller: 'AdminUserPasswdCtrl as ctrl',
				size: 'md',
				resolve: {
					user: function() { return user; }
				}
			});

			modal.result.then(ctrl.search);
		}

		function openUserForm(user, changeSecret) {
			var modal = $modal.open({
				templateUrl: 'app/admin/user/user.form.html',
				controller: 'AdminUserFormCtrl as ctrl',
				size: 'md',
				resolve: {
					user: function() { return user; },
					changeSecret: function() { return changeSecret; }
				}
			});

			modal.result.then(success);

			function success(result) {
				ctrl.searchParams.q = result.username;
				ctrl.search();
			}
		}

		function removeUser(user) {
			angularPopupBoxes.confirm('Are you sure want to delete this data?')
					.result.then(remove);

			function remove() {
				user.remove().then(ctrl.search);
			}
		}

		function searchUsers() {
			ctrl.searchParams.hash++;
			ctrl.searchParams.page = ctrl.page - 1;

			$location.search(ctrl.searchParams);

			RestUserService.getList(ctrl.searchParams).then(onUsersLoaded);
		}
	}
})();
