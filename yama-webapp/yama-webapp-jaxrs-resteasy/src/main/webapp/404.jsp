<% 
	String ns = request.getContextPath();
	if (ns.equalsIgnoreCase("/")) ns = "";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Page Not Found</title>
		<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
		<!-- bootstrap 3.0.2 -->
		<link href="<%= ns %>/webjars/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<!-- font Awesome -->
		<link href="<%= ns %>/webjars/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<!-- Theme style -->
		<link href="<%= ns %>/styles/adminlte/adminlte.css" rel="stylesheet" type="text/css" />

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
				<div class="error-page">
					<h2 class="headline text-info"> 404</h2>
					<div class="error-content">
						<h3><i class="fa fa-warning text-yellow"></i> Oops! Page not found.</h3>
						<p>
							We could not find the page you were looking for. 
							Meanwhile, you may <a href="../../index.html">return to dashboard</a> or try using the search form.
						</p>
						<form class="search-form">
							<div class="input-group">
								<input type="text" name="search" class="form-control" placeholder="Search">
								<div class="input-group-btn">
									<button type="submit" name="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
								</div>
							</div><!-- /.input-group -->
						</form>
					</div><!-- /.error-content -->
				</div><!-- /.error-page -->
	</body>
</html>
