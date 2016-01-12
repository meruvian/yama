(function() {
	'use strict';

	angular.module('yamaApp').directive('pushMenu', pushMenu);

	function pushMenu() {
		var directive = {
			link: link,
			restrict: 'A',
			scope: {
				model: '=ngModel',
				expandOnHover: '@'
			}
		};

		return directive;

		function link(scope, element) {
			var screenSizes = {
				xs: 480,
				sm: 768,
				md: 992,
				lg: 1200
			};

			//Enable sidebar toggle
			element.on('click', function (e) {
				e.preventDefault();

				//Enable sidebar push menu
				if ($(window).width() > (screenSizes.sm - 1)) {
					$('body').toggleClass('sidebar-collapse');
				}
				//Handle sidebar push menu for small screens
				else {
					if ($('body').hasClass('sidebar-open')) {
						$('body').removeClass('sidebar-open');
						$('body').removeClass('sidebar-collapse');
					} else {
						$('body').addClass('sidebar-open');
					}
				}
			});

			$('.content-wrapper').click(function () {
				//Enable hide menu when clicking on the content-wrapper on small screens
				if ($(window).width() <= (screenSizes.sm - 1) && $('body').hasClass('sidebar-open')) {
					$('body').removeClass('sidebar-open');
				}
			});

			//Enable expand on hover for sidebar mini
			if (scope.expandOnHover || ($('body').hasClass('fixed') &&
					$('body').hasClass('sidebar-mini'))) {
				expandOnHover();
			}

			function collapse() {
				if ($('body').hasClass('sidebar-expanded-on-hover')) {
					$('body').removeClass('sidebar-expanded-on-hover').addClass('sidebar-collapse');
				}
			}

			function expand() {
				$('body').removeClass('sidebar-collapse').addClass('sidebar-expanded-on-hover');
			}

			function expandOnHover() {
				var screenWidth = screenSizes.sm - 1;
				//Expand sidebar on hover
				$('.main-sidebar').hover(function () {
					if ($('body').hasClass('sidebar-mini') && $('body').hasClass('sidebar-collapse') &&
						$(window).width() > screenWidth) {
						expand();
					}
				}, function () {
					if ($('body').hasClass('sidebar-mini') && $('body').hasClass('sidebar-expanded-on-hover') &&
						$(window).width() > screenWidth) {
						collapse();
					}
				});
			}
		}
	}
})();
