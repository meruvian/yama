<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${title!} - <@s.text name="page.main.title" /></title>
		<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
		<!-- bootstrap 3.0.2 -->
		<link href="<@s.url value="/webjars/bootstrap/3.1.1/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
		<!-- font Awesome -->
		<link href="<@s.url value="/webjars/font-awesome/4.0.3/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
		<!-- Theme style -->
		<link href="<@s.url value="/styles/adminlte/adminlte.css" />" rel="stylesheet" type="text/css" />

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
		${body!}

		<!-- jQuery 2.0.2 -->
		<script src="<@s.url value="/webjars/jquery/1.11.0/jquery.min.js" />"></script>
		<!-- Bootstrap -->
		<script src="<@s.url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js" />" type="text/javascript"></script>	 
	</body>
</html>