<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title><@s.text name="frontend.title" /></title>
		
		<script type="text/javascript" src="<@s.url value="/static/jquery/jquery.min.js" />"></script>
		<script type="text/javascript" src="<@s.url value="/static/jquery/jquery.address.min.js" />"></script>
		<script type="text/javascript" src="<@s.url value="/scripts/inca.ext.js" />"></script>
		<script type="text/javascript">
		var ajax = {};
		ajax.fullContextPath = '<@s.url value="/" forceAddSchemeHostAndPort="true" />';
		ajax.contextPath = '<@s.url value="/" />';
		ajax.load = function(url) {
			jQuery.ajax({
				type : "GET",
				url : this.fullContextPath + url,
				success : function(data) {
					data = data.replace(/<script.*>.*<\/script>/ig,""); // Remove script tags
					data = data.replace(/<\/?link.*>/ig,""); //Remove link tags
					data = data.replace(/<\/?html.*>/ig,""); //Remove html tag
					data = data.replace(/<\/?body.*>/ig,""); //Remove body tag
					data = data.replace(/<\/?head.*>/ig,""); //Remove head tag
					data = data.replace(/<\/?!doctype.*>/ig,""); //Remove doctype
					data = data.replace(/<title.*>.*<\/title>/ig,""); // Remove title tags
					
					$('#content').empty().html(data);
					
					window.inca.init();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					$('#content').empty();
					$('#content').append('<h2>HTTP Status ' + jqXHR.status +' - ' + textStatus +'</h2>');
					$('#content').append(errorThrown);
				}
			});
		};
		
		$(function() {
			$.address.externalChange(function() {
				if(location.hash == "" || location.hash == "#") {
					$('#content').empty();
					ajax.load('/welcome');
				} else {
					ajax.load(location.hash.substring(2));
				}
			});
			
			$('#loading').overflow().hide();
			$('#loading').css("position", "fixed");
			$('#loading').width($(window).width());
			$('#loading').height($(window).height());
			$('#loading-dialog').css('z-index', 1001);
			$('#loading-dialog').center();
			
			$(document).ajaxStart(function() {
				$('#loading').hide();
				$('#loading').fadeIn();
			});

			$(document).ajaxStop(function() {
				$('#loading').show();
				$('#loading').hide();
			});
		});
		</script>
		<@sb.head compressed="true" />
		<@s.url value="" forceAddSchemeHostAndPort="true" var="ctx" />
		<style type="text/css">
			button.btn, input.btn { min-width: 100px; }
			.navbar-fixed-top .brand { 
				color: #FFF; 
				float: left; 
				margin-right: 10px; 
				text-shadow: 0 px 0 rgba(100,100,100,.1), 0 0 30px rgba(100,100,100,.125); 
			}
			header { margin-bottom: 18px; border-bottom: 2px solid #777; }
			header h1 { font-size: 150%; margin-bottom: 5px; }
		</style>
	</head>
	<body>
		<div id="loading">
			<div id="loading-dialog"><img src="<@s.url value="/images/loading.gif" />" /></div>
		</div>
		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</a>
					<a class="brand" href="<@s.url value="/" />"><@s.text name="frontend.header.text" /></a>
					<div class="nav-collapse">
						<ul class="nav">
							<li class="active"><a href='<@s.url value="/home" />'><i class="icon-home icon-white"></i> Home</a></li>
							<#if request.session.getAttribute("SPRING_SECURITY_CONTEXT")??>
								<li>
									<a href="<@s.url value="/logout" />">
										<@s.text name="frontent.logout.text" />
									</a>
								</li>
							</#if>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
		<div class="container content" id="content">
			<#-- ${body!} -->
		</div>
		
		<footer class="footer modal-footer">
			<div class="container">
				<@s.text name="frontend.footer.text" />
			</div>
		</footer>
	</body>
</html>
