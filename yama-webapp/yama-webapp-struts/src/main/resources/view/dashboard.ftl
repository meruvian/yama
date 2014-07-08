<html>
	<head>
		<meta charset="UTF-8">
		<title><@s.text name="page.dashboard.title" /></title>
	</head>
	<body>
		<content tag="header"><@s.text name="page.dashboard.title" /></content>
		
		<h3>Welcome! you have logged in successfully.</h3>
		<ul>
			<li><a href="<@s.url value="/profile" />">Update Profile</a></li>
			<li><a href="<@s.url value="/applications" />">Register an Application</a></li>
		</ul>
	</body>
</html>