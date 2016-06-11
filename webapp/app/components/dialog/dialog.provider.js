(function() {
	'use strict';

	angular.module('yamaDialog').provider('Dialog', yamaDialogProvider);

	function yamaDialogProvider() {
		var q, uibModal;
		/* jshint validthis: true */
		var vm = this;
		vm.$get = $get;
		vm.alert = alert;
		vm.confirm = confirm;
		vm.input = input;
		vm.cancelText = 'Cancel';
		vm.okText = 'Ok';
		vm.setTexts = setTexts;

		function $get($uibModal, $q) {
			'ngInject';
			uibModal = $uibModal;
			q = $q;

			return {
				alert: this.alert,
				confirm: this.confirm,
				input: this.input,
				setTexts: this.setTexts
			};
		}

		function alert(content, options) {
			options = extendOptions(options, {
				okText: vm.okText,
				cancelText: false
			});

			return buildModal(content, options);
		}

		function buildModal(content, options) {
			var modalEl;
			var modalId = 'modal-' + Math.floor(Math.random() * 9999);
			var modal = buildUibModal();

			setTimeout(buildModalEl, 1);

			function buildHtml() {
				var html  = '<div class="modal-body" id="' + modalId +  '">';
				html += content;
				html += '</div>';
				html += '<div class="modal-footer">';
				if (typeof options.cancelText === 'string') {
					html += '<button class="btn btn-sm btn-danger angular-notification-btn-cancel">' + options.cancelText + '</button>';
				}
				if (typeof options.okText === 'string') {
					html += '<button class="btn btn-sm btn-primary angular-notification-btn-ok">' + options.okText + '</button>';
				}
				html += '</div>';

				return html;
			}

			function buildModalEl() {
				modalEl = $('#' + modalId).parent().parent();
				modalEl.width(options.width);
				modalEl.find('.angular-notification-btn-ok').click(function() {
					modal.close();
				});
				modalEl.find('.angular-notification-btn-cancel').click(function() {
					modal.dismiss();
				});

				modal.el = modalEl;
			}

			function buildUibModal() {
				return uibModal.open({
					template: buildHtml(),
					keyboard: false,
					backdrop: 'static'
				});
			}

			return modal;
		}

		function confirm(content, options) {
			options = extendOptions(options, {
				okText: vm.okText,
				cancelText: vm.cancelText
			});

			return buildModal(content, options);
		}

		function extendOptions(options, defaults) {
			options = options || {};

			if (typeof options.okText === 'undefined') {
				options.okText = defaults.okText;
			}

			if (typeof options.cancelText === 'undefined') {
				options.cancelText = defaults.cancelText;
			}

			if (typeof options.width === 'undefined') {
				options.width = 370;
			}

			return options;
		}

		function input(content, options) {
			content += '<br/><br/><form class="form"><input name="input" class="form-control angular-notification-input" /></form>';

			options = extendOptions(options, {
				okText: vm.okText,
				cancelText: vm.cancelText
			});

			var modal = buildModal(content, options);
			var deferred = q.defer();
			var input;

			setTimeout(buildInput, 3);
			modal.result.then(onSuccess, deferred.reject);
			modal.result = deferred.promise;

			function buildInput() {
				input = modal.el.find('.angular-notification-input');

				$(window).one('keydown', function() {
					input.focus();
				});

				input.on('keyup', function(e) {
					if (e.keyCode === 13)
					{
						modal.close();
					}
				});
			}

			function onSuccess() {
				var val = modal.el.find('.angular-notification-input').val();
				input.off();

				if (val === '') {
					deferred.reject();
				} else {
					deferred.resolve(val);
				}
			}

			return modal;
		}

		function setTexts(okText, cancelText) {
			vm.okText = okText;
			vm.cancelText = cancelText;
		}
	}
})();
