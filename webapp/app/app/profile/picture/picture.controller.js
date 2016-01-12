(function() {
	'use strict';

	angular.module('yamaApp').controller('ProfilePictureCtrl', profilePictureController);

	function profilePictureController(AppService, ProfilePictureService) {
		// jshint validthis: true
		var ctrl = this;
		ctrl.photos = [];
		ctrl.upload = uploadPhoto;
		ctrl.uploadProgress = -1;
		loadPhoto();

		function loadPhoto() {
			ctrl.photos.shift();
			ctrl.photos.push({
				photoUrl: ProfilePictureService.getPhotoUrl()
			});
			ctrl.uploadProgress = -1;

			AppService.updateCurrentUserPhoto();
		}

		function uploadPhoto(file) {
			ProfilePictureService.uploadPhoto(file, progress, loadPhoto);

			function progress(evt) {
				ctrl.uploadProgress = parseInt(100.0 * evt.loaded / evt.total);
			}
		}
	}
})();
