(function() {
	'use strict';

	angular.module('yamaApp').controller('AdminApplicationListCtrl', applicationListController);

	function applicationListController($location, $uibModal, Dialog, RestApplicationService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.openForm = openApplicationForm;
		ctrl.page = 1;
		ctrl.remove = removeApplication;
		ctrl.searchParams = $location.search();
		ctrl.searchParams.hash = 0;
		ctrl.search = searchApplications;
		ctrl.search();

		function onApplicationsLoaded(applications) {
			ctrl.applications = applications;
			ctrl.page = applications.meta.number + 1;
		}

		function openApplicationForm(application, changeSecret) {
			var modal = $uibModal.open({
				templateUrl: 'app/admin/application/application.form.html',
				controller: 'AdminApplicationFormCtrl as ctrl',
				size: 'md',
				resolve: {
					application: function() { return application; },
					changeSecret: function() { return changeSecret; }
				}
			});

			modal.result.then(success);

			function success(result) {
				ctrl.searchParams.q = result.name;
				ctrl.search();
			}
		}

		function removeApplication(application) {
			Dialog.confirm('Are you sure want to delete this data?')
					.result.then(remove);

			function remove() {
				application.remove().then(ctrl.search);
			}
		}

		function searchApplications() {
			ctrl.searchParams.hash++;
			ctrl.searchParams.page = ctrl.page - 1;

			$location.search(ctrl.searchParams);

			RestApplicationService.getList(ctrl.searchParams).then(onApplicationsLoaded);
		}
	}
})();
