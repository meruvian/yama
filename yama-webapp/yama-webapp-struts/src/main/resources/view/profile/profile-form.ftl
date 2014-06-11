<html>
	<head>
		<meta charset="UTF-8">
		<title>Dashboard</title>
	</head>
	<body>
		<content tag="header">Profile</content>
		<content tag="headerDetail">Edit Profile</content>
		
		<@s.actionerror theme="bootstrap"/>
		<@s.actionmessage theme="bootstrap"/>
		<@s.fielderror theme="bootstrap"/>
		<div class="row">
			<div class="col-md-6">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Basic</h3>
					</div>
					
					<div class="box-body">
						<@s.form theme="bootstrap">
							<@s.hidden name="user.id" />
							<@s.textfield label="Username" name="user.username" />
							<div class="row">
								<div class="col-md-6">
									<@s.textfield label="First Name" name="user.name.first" />
								</div>
								<div class="col-md-6">
									<@s.textfield label="Last Name" name="user.name.last" />
								</div>
							</div>
							<@s.textfield label="Email" name="user.email" />
							
							<@s.submit cssClass="btn btn-primary col-md-3" value="Save" />
						</@s.form>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="row">
					<div class="col-md-12">
						<div class="box box-success">
							<div class="box-header">
								<h3 class="box-title">Profile Picture</h3>
							</div>
							
							<div class="box-body">
								<@s.form theme="bootstrap">
									<@s.file label="Browse" name="user.photo" />
								</@s.form>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="box box-success">
							<div class="box-header">
								<h3 class="box-title">Change Password</h3>
							</div>
							
							<div class="box-body">
								<@s.form theme="bootstrap">
									<@s.hidden name="user.id" />
									<@s.password label="Current Password" name="cpassword" />
									<div class="row">
										<div class="col-md-6">
											<@s.password label="New Password" name="user.password" />
										</div>
										<div class="col-md-6">
											<@s.password label="Confirm New Password" />
										</div>
									</div>
									
									<@s.submit cssClass="btn btn-primary col-md-3" value="Save" />
								</@s.form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>