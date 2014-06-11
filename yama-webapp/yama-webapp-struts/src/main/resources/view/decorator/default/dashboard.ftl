<!DOCTYPE html>
<html ng-app="yama">
	<head>
		<meta charset="UTF-8">
		<title>${title!}</title>
		<base href="<@s.url value="/" />" />
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
		
		${head!}
	</head>
	<body class="skin-blue">
		<!-- header logo: style can be found in header.less -->
		<#include "nav/top.ftl" />
		<div class="wrapper row-offcanvas row-offcanvas-left">
			<!-- Left side column. contains the logo and sidebar -->
			<#include "nav/side.ftl" />
		
			<!-- Right side column. Contains the navbar and content of the page -->
			<aside class="right-side">
				<!-- Content Header (Page header) -->
				<section class="content-header">
					<h1>
						${page.getProperty('page.header')!}
						<small>${page.getProperty('page.headerDetail')!}</small>
					</h1>
					<ol class="breadcrumb">
						${page.getProperty('page.breadcrumb')!}
						<#--
						<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
						<li class="active">Dashboard</li>
						-->
					</ol>
				</section>

				<!-- Main content -->
				<section class="content">
					<!-- top row -->
					<div class="row">
						<div class="col-xs-12 connectedSortable">
						${body}
						</div><!-- /.col -->
					</div>
					<!-- /.row -->
				</section><!-- /.content -->
			</aside><!-- /.right-side -->
		</div><!-- ./wrapper -->

		<!-- add new calendar event modal -->


		<!-- jQuery 2.0.2 -->
		<script src="<@s.url value="/webjars/jquery/1.11.0/jquery.min.js" />"></script>
		<!-- Bootstrap -->
		<script src="<@s.url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js" />" type="text/javascript"></script>
		<!-- AdminLTE App -->
		<script src="<@s.url value="/scripts/adminlte/app.js" />" type="text/javascript"></script>
		${page.getProperty('page.script')!}
	</body>
</html>