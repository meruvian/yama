<html>
	<head>
		<title><@s.text name="page.admin.role.title" /></title>
	</head>
	<body>
		<content tag="header"><@s.text name="page.admin.role.header" /></content>
		<content tag="script">
		<script type="text/javascript">
		</script>
		</content>

		<@s.actionerror theme="bootstrap"/>
		<@s.actionmessage theme="bootstrap"/>
		<div class="row">
			<div class="col-md-9">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title"><@s.text name="label.admin.role.title" /></h3>
					</div>
					
					<div class="box-body">
						<@s.form theme="bootstrap">
							<@s.hidden name="role.id" />
							<@s.textfield key="label.admin.role.name" name="role.name" value="${(role.name!'')?upper_case}" />
							<@s.textarea rows="2" key="label.admin.role.description" name="role.description" />
							
							<@s.submit cssClass="btn btn-primary col-md-3" value="%{getText('button.main.save')}" />
						</@s.form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>