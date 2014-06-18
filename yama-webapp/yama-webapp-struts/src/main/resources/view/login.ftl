<html>
	<head>
		<meta charset="UTF-8">
		<title><@s.text name="page.login.title" /></title>
	</head>
	<body>
		<div class="form-box" id="login-box">
			<div class="header bg-gray">
				<img src="<@s.url value="/images/logo.png" />" />
			</div>
			<@s.form action="${request.contextPath}${request.servletPath}/do" method="post" theme="bootstrap">
				<div class="body bg-gray">
					<@s.textfield key="label.login.username" name="username" />
					<@s.textfield key="label.login.password" name="password" />
					<div class="form-group">
						<input type="checkbox" name="_spring_security_remember_me"/> <@s.text name="label.login.rememberme" />
					</div>
				</div>
				<div class="footer bg-gray">															   
					<button type="submit" class="btn bg-blue btn-block"><@s.text name="button.main.login" /></button>  
					
					<a href="<@s.url value="/register" />" class="text-center"><@s.text name="label.login.register" /></a>
				</div>
			</@s.form>

			<div class="margin text-center">
				<span><@s.text name="label.login.social" /></span>
				<br />
				<a class="btn bg-light-blue btn-circle" href="<@s.url value="/login/social/facebook/auth" />">
					<i class="fa fa-facebook"></i>
				</a>
				<a class="btn bg-red btn-circle" href="<@s.url value="/login/social/google/auth" />">
					<i class="fa fa-google-plus"></i>
				</a>
			</div>
		</div>
	</body>
</html>