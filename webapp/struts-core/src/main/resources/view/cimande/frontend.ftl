<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title><@s.text name="frontend.title" /></title>
	
	<link rel="stylesheet" type="text/css" href="<@s.url value="/styles/map/leaflet/leaflet.css" />" />
	
	<link href="/styles/reset.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
	
	<link rel="shortcut icon" href="ico/favicon.ico">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png">
	
	<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
	<!--[if lt IE 9]>
	<link rel="stylesheet" type="text/css" href="<@s.url value="/styles/map/leaflet/leaflet.ie.css" />" />
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	<script src="/scripts/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<@s.url value="/scripts/inca.ext.js" />"></script>
	<script type="text/javascript" src="<@s.url value="/scripts/map/leaflet/leaflet.js" />"></script>
	<@sb.head compressed="true" />
	<@s.url value="" forceAddSchemeHostAndPort="true" var="ctx" />
	
	<style type="text/css">
	body {
				/* padding-top: 80px; */
	}
	button.btn, input.btn { min-width: 100px; }
		.navbar-fixed-top .brand { 
		color: #FFF; 
		float: left; 
		margin-right: 10px; 
		text-shadow: 0 px 0 rgba(100,100,100,.1), 0 0 30px rgba(100,100,100,.125); 
		}
	header { margin-bottom: 18px; border-bottom: 2px solid #777; }
	header h1 { font-size: 150%; margin-bottom: 5px; }
	.required{
		color: red;
	}
	.bs-docs-example {
		position: relative;
		margin: 0 0 15px;
		padding: 39px 0px 14px;
		background-color: white;
		border: 1px solid  #DDD;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
		border-radius: 4px;
	}
	.title-news{
		font-size: 17px;		
		font-weight: bold;
	}
	.bs-docs-example::after {
		content: "Article";
		position: absolute;
		top: -1px;
		left: -1px;
		padding: 9px 11px;
		font-size: 22px;
		font-weight: bold;
		background-color: whiteSmoke;
		border: 1px solid #DDD;
		color: #317EAC;
		-webkit-border-radius: 4px 0 4px 0;
		-moz-border-radius: 4px 0 4px 0;
		border-radius: 4px 0 4px 0;
	}
	#comment {
		margin-left: 0px;
	}
	#comment::after {
		content: "Komentar";
		position: absolute;
		top: -1px;
		left: -1px;
		padding: 3px 7px;
		font-size: 15px;
		background-color: whiteSmoke;
		border: 1px solid #DDD;
		color: #317EAC;
		-webkit-border-radius: 4px 0 4px 0;
		-moz-border-radius: 4px 0 4px 0;
		border-radius: 4px 0 4px 0;
	}
	#newsfeed {
		margin-left: 0px;
	}
	#newsfeed::after {
		content: "News Feed";
		position: absolute;
		top: -1px;
		left: -1px;
		padding: 9px 11px;
		font-size: 22px;
		font-weight: bold;
		background-color: whiteSmoke;
		border: 1px solid #DDD;
		color: #317EAC;
		-webkit-border-radius: 4px 0 4px 0;
		-moz-border-radius: 4px 0 4px 0;
		border-radius: 4px 0 4px 0;
	}
	
		</style>
	
</head>

<body>
<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li>
							<a class="brand" href="<@s.url value="/" />"><@s.text name="frontend.header.text" /></a>
						</li>
					</ul>
					<ul class="nav pull-right ">

						<#if request.session.getAttribute("SPRING_SECURITY_CONTEXT")??>

						<li>
							<#if request.session.getAttribute("cimandeSecurityUser")??>
								<#assign name = request.session.getAttribute("cimandeSecurityUser").name />								
							
									<a title="Profil" href="/module/profile"><i class="icon-user icon-white"></i> &nbsp; ${name.first} ${name.last}</a>
							</#if>
						</li>
						<li>
							<a title="Keluar" href="<@s.url value="/logout" />">
									<i class="icon-off icon-white"></i>&nbsp;
							</a>
						</li>
						<#else>
							<li>
								<a href="<@s.url value="/user/signup" />"><i class="icon-user icon-white"></i>&nbsp;&nbsp;<@s.text name="frontend.signup.text" /></a>
							</li>
							<li>

							<a href="<@s.url value="/login?redirect_uri=" />${request.getRequestURI().substring(request.getContextPath().length())}"><i class="icon-check icon-white"></i>&nbsp;&nbsp;<@s.text name="frontend.login.text" /></a>

							</li>
						</#if>
						<#if request.session.getAttribute("cimandeSecurityRole")??>
							<li>
								<a href="<@s.url value="/backend" />" title="<@s.text name="frontend.go.backend.text" />">&nbsp;<i class="icon-share icon-white"></i></a>
							</li>
						</#if>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="container content" id="content">

	${body}
	</div>
	
	<@s.url id="localeEN" namespace="/" action="home" >
   	<@s.param name="request_locale" >en</@s.param>
	</@s.url>
	<@s.url id="localezhID" namespace="/" action="home" >
   	<@s.param name="request_locale" >in_ID</@s.param>
	</@s.url>

	<footer class="footer" id="footer">
		<div class="container">
			<div class="span3"><@s.text name="frontend.footer.text" /></div>
			<div class="span8" style="text-align: right;">
				<a href="#"><@s.text name="footer.about.us.text" /></a> &nbsp; 
				<a href="${localeEN}">English</a>&nbsp; 
				<a href="${localezhID}">Indonesia</a> 
				
			</div>
		</div>
	</footer>

	<script type="text/javascript">
	
	$(document).ready(function(){
		$('.carousel').carousel({
			interval: 4000
		});
		
	  	$('.dropdown-toggle').dropdown();
	
		$('.dropdown input, .dropdown label').click(function(e) {
			e.stopPropagation();
	  	});
	  	
	  	$("span.required").each(function() {
	  		var parent = $(this).parent();
	  		$(this).prependTo(parent);
	  	});
	});
	</script>
	
</body>
</html>
