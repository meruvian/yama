<html>
	<head>
		<meta charset="UTF-8">
		<title><@s.text name="page.register.title" /></title>
	</head>
	<body>
		<div class="form-box" id="login-box">
			<div class="header bg-gray">
				<img src="<@s.url value="/images/logo.png" />" />
			</div>
			<@s.form theme="bootstrap">
				<div class="body bg-gray">
					<@s.textfield name="user.username" key="label.register.username" />
					<@s.password name="user.password" key="label.register.password" />
					<@s.password name="confirmPassword" key="label.register.password.confirm" />
					<@s.textfield name="user.email" key="label.register.email" />
					<#if reCaptchaActive>
					<div class="label label-danger col-md-12">${fieldErrors.get('user.reCaptcha')!}</div>
					<#include "recaptcha.ftl" />
					</#if>
				</div>
				<div class="footer bg-gray">
					<button type="submit" class="btn bg-blue btn-block"><@s.text name="button.main.signup" /></button>
					<a href="<@s.url value="/login" />" class="text-center"><@s.text name="label.register.login" /></a>
				</div>
			</@s.form>

			<div class="margin text-center">
				<span><@s.text name="label.register.social" /></span>
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