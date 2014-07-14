<html>
	<head>
		<meta charset="UTF-8">
		<title><@s.text name="page.approval.title" /></title>
	</head>
	<body>
		<div class="form-box">
			<div class="header bg-gray">
				<img src="<@s.url value="/images/logo.png" />" />
			</div>
			<@s.form action="/oauth/authorize" method="post" theme="bootstrap">
				<div class="body bg-gray">
					<@s.hidden name="user_oauth_approval" value="true" />
					<h4>
						<@s.text name="label.oauth.approval">
							<@s.param><strong>${app.displayName!}</strong></@s.param>
						</@s.text>
					</h4>
				</div>
				<div class="footer bg-gray">															   
					<button type="submit" class="btn bg-green btn-block"><@s.text name="button.main.authorize" /></button>
				</div>
			</@s.form>
			<@s.form action="/oauth/authorize" method="post" theme="bootstrap">
				<div class="footer bg-gray">
					<@s.hidden name="user_oauth_approval" value="false" />
					<button type="submit" class="btn bg-red btn-block"><@s.text name="button.main.deny" /></button>
				</div>
			</@s.form>
		</div>
	</body>
</html>