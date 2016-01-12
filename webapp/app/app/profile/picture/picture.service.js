(function() {
	'use strict';

	angular.module('yamaApp').factory('ProfilePictureService', profilePictureService);

	function profilePictureService(RestUserService, Upload, YamaOAuth) {
		return {
			getPhotoUrl: getPhotoUrl,
			uploadPhoto: uploadPhoto
		};

		function getPhotoUrl() {
			return RestUserService.one('me').one('photo').getRequestedUrl() +
					'?access_token=' + YamaOAuth.getAccessToken().access_token +
					'&cache=' + (new Date()).getTime();
		}

		function uploadPhoto(file, progress, success, error) {
			var imageUrl = RestUserService.one('me').one('photo').getRequestedUrl();

			Upload.http({ data: file, url: imageUrl, headers : { 'Content-Type': file.type } })
				.progress(progress)
				.success(success)
				.error(error);
		}
	}
})();
