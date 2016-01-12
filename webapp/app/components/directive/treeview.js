(function() {
	'use strict';

	angular.module('yamaApp').directive('treeView', treeView);

	function treeView() {
		var directive = {
			link: link,
			restrict: 'A',
			scope: {
				model: '=ngModel'
			}
		};

		return directive;

		function link(scope, element) {
			var animationSpeed = 500;

			element.on('click', 'li a', onClick);

			function onClick(e) {
				// Get the clicked link and the next element
				// jshint validthis: true
				var $this = $(this);
				var checkElement = $this.next();

				// Check if the next element is a menu and is visible
				if (checkElement.is('.treeview-menu') && checkElement.is(':visible') &&
						!($('body').hasClass('sidebar-collapse'))) {
					// Close the menu
					checkElement.slideUp(animationSpeed, function() {
						checkElement.removeClass('menu-open');
					});

					checkElement.parent('li').removeClass('active');
					// If the menu is not visible
				} else if (checkElement.is('.treeview-menu') && !(checkElement.is(':visible'))) {
					// Get the parent menu
					var parent = $this.parents('ul').first();
					// Close all open menus within the parent
					var ul = parent.find('ul:visible').slideUp(animationSpeed);
					// Remove the menu-open class from the parent
					ul.removeClass('menu-open');
					// Get the parent li
					var parent_li = $this.parent('li');

					// Open the target menu and add the menu-open class
					checkElement.slideDown(animationSpeed, function() {
						// Add the class active to the parent li
						checkElement.addClass('menu-open');
						parent.find('li.active').removeClass('active');
						parent_li.addClass('active');
						// Fix the layout in case the sidebar stretches over the height of the window
						// _this.layout.fix()
						$.AdminLTE.layout.fix();
					});
				}

				// if this isn't a link, prevent the page from being redirected
				if (checkElement.is('.treeview-menu')) {
					e.preventDefault();
				}
			}
		}
	}
})();
