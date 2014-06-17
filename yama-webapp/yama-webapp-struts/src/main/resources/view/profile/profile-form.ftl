<html>
	<head>
		<meta charset="UTF-8">
		<title><@s.text name="page.profile.title" /></title>
	</head>
	<body>
		<content tag="header"><@s.text name="page.profile.header" /></content>
		
		<@s.actionerror theme="bootstrap"/>
		<@s.actionmessage theme="bootstrap"/>
			<div class="row">
			<div class="col-md-6">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Basic</h3>
					</div>
					
					<div class="box-body">
						<@s.form theme="bootstrap">
							<@s.hidden name="user.id" />
							<@s.textfield key="label.profile.username" name="user.username" />
							<div class="row">
								<div class="col-md-6">
									<@s.textfield key="label.profile.name.first" name="user.name.first" />
								</div>
								<div class="col-md-6">
									<@s.textfield key="label.profile.name.last" name="user.name.last" />
								</div>
							</div>
							<@s.textfield key="label.profile.email" name="user.email" />
							
							<@s.submit cssClass="btn btn-primary col-md-3" value="%{getText('button.main.save')}" />
						</@s.form>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="row">
					<div class="col-md-12">
						<div class="box box-success">
							<div class="box-header">
								<h3 class="box-title"><@s.text name="label.profile.picture" /></h3>
							</div>
							
							<div class="box-body">
								<@s.form theme="bootstrap" enctype="multipart/form-data">
									<div class="row">
										<img src="<@s.url value="/profile/photo" />" class="img-thumbnail col-md-3 col-md-offset-4" alt="User Image">
									</div>
									<@s.hidden name="user.id" />
									<@s.hidden name="edit" value="photo" />
									<@s.file key="button.main.browse" name="profilePicture" />
									
									<@s.submit cssClass="btn btn-primary col-md-3" value="%{getText('button.main.upload')}" />
								</@s.form>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="box box-success">
							<div class="box-header">
								<h3 class="box-title"><@s.text name="label.profile.password.change" /></h3>
							</div>
							
							<div class="box-body">
								<@s.form theme="bootstrap">
									<@s.hidden name="user.id" />
									<@s.hidden name="edit" value="password" />
									<div class="row">
										<div class="col-md-6">
											<@s.password key="label.profile.password" name="user.password" />
										</div>
										<div class="col-md-6">
											<@s.password key="label.profile.password.confirm" name="confirmPassword" />
										</div>
									</div>
									
									<@s.submit cssClass="btn btn-primary col-md-3" value="%{getText('button.main.save')}" />
								</@s.form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>