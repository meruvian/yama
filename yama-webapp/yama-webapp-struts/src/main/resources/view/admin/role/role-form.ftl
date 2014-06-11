<html>
	<head>
		<title>Role</title>
	</head>
	<body>
		<content tag="header">Role</content>
		<content tag="headerDetail">Add New Role</content>
		<content tag="script">
		<script type="text/javascript">
		</script>
		</content>

		<@s.actionerror theme="bootstrap"/>
		<@s.actionmessage theme="bootstrap"/>
		<@s.fielderror theme="bootstrap"/>
		<div class="row">
			<div class="col-md-9">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Role Information</h3>
					</div>
					
					<div class="box-body">
						<@s.form theme="bootstrap">
							<@s.hidden name="role.id" />
							<@s.textfield label="Name" name="role.name" value="${(role.name!'')?upper_case}" />
							<@s.textarea rows="2" label="Description" name="role.description" />
							
							<@s.submit cssClass="btn btn-primary col-md-3" value="Save" />
						</@s.form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>