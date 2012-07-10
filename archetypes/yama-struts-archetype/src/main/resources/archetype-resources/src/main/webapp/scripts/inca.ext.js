(function(w) {
	w.inca = {};

	w.inca.init = function() {
		$('#loading').width($(window).width());
		$('#loading').height($(window).height());

		$('#content').find('a').each(function() {
			if ($(this).attr('href') != null) {
				var href = $(this).attr('href');
				var namespace = location.hash.substring(0, location.hash.lastIndexOf("/")) + "/";
				if ($(this).attr('target') == '_blank') {
					$(this).attr('href', href);
				} else {
					if (href.charCodeAt(0) != 47) {
						$(this).attr('href', namespace + href);
					} else {
						$(this).attr('href', '#' + href);
					}
				}
			} else {
				return null;
			}
		});

		$('#content').find('style').each(function() {
			var href = $(this).attr('href');
			href = href == undefined ? '' : href;

			var namespace = location.hash.substring(2, location.hash.lastIndexOf("/")) + "/";
			if (href.charCodeAt(0) != 47) {
				$(this).attr('href', ajax.fullContextPath + namespace + href);
			}
		});

		$('#content').find('script, img').each(function() {
			var href = $(this).attr('src');
			href = href == undefined ? '' : href;

			var namespace = location.hash.substring(2, location.hash.lastIndexOf("/")) + "/";
			if (href.charCodeAt(0) != 47) {
				$(this).attr('src', ajax.fullContextPath + namespace + href);
			}
		});

		$('#content').find('a.button').each(function() {
			$(this).prev('button.abutton').remove();

			var anchor = $(this);
			anchor.hide();
			var text = anchor.text();
			var btn = $(document.createElement("button"));
			btn.addClass('abutton');
			btn.text(anchor.text());
			btn.click(function() {
				location.href = anchor.attr('href');
			});
			anchor.before(btn);
		});

		$('#content').find('form').submit(function() {
			var requestMethod = $(this).attr('method');
			var href = $(this).attr('action');
			href = href == undefined ? '' : href;
			var namespace = ajax.fullContextPath;
			namespace += location.hash.substring(2, location.hash.lastIndexOf("/")) + "/";
			if ($(this).attr('target') != '_blank') {
				if (href.charCodeAt(0) != 47) {
					href = namespace + href;
				}
				
				jQuery.ajax({
					type : requestMethod,
					data : $(this).serialize(),
					url : href,
					success : function(data) {
						data = data.replace(/<script.*>.*<\/script>/ig, "");
						data = data.replace(/<\/?link.*>/ig, "");
						data = data.replace(/<\/?html.*>/ig, "");
						data = data.replace(/<\/?body.*>/ig, "");
						data = data.replace(/<\/?head.*>/ig, "");
						data = data.replace(/<\/?!doctype.*>/ig, "");
						data = data.replace(/<title.*>.*<\/title>/ig, "");

						$('#content').empty().html(data);

						window.inca.init();
					}
				});
				return false;
			} else {

			}
		});
	};
})(window);

(function($) {
	$.fn.center = function () {
		this.css("position","absolute");
		this.css("top", (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop() + "px");
		this.css("left", (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft() + "px");
		
		return this;
	};

	$.fn.overflow = function () {
		this.css({
			'width' : window.outerWidth,
			'top' : 0,
			'bottom' : 0,
			'position' : 'absolute',
			'z-index' : 99999,
			'background-color' : '#EEE',
			'opacity' : '.50'
		});

		return this;
	};
})(jQuery);