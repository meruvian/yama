<html>
	<head>
		<title><@s.text name="page.application.title" /></title>
	</head>
	<body>
		<content tag="header"><@s.text name="page.application.header" /></content>
		<content tag="script">
		<script type="text/javascript">
		</script>
		</content>

		<@s.actionerror theme="bootstrap"/>
		<@s.actionmessage theme="bootstrap"/>
		<div class="row">
			<div class="col-md-7">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title"><@s.text name="label.application.title" /></h3>
					</div>
					
					<div class="box-body">
						<@s.form theme="bootstrap">
							<#if (app.id!'')?trim != ''>
							<@s.textfield key="label.application.id" name="app.id" readonly="true" />
							</#if>
							<@s.textfield key="label.application.name" name="app.displayName" />
							<@s.textfield key="label.application.site" name="app.site" />
							
							<#if isAdmin>
							<@s.checkbox key="label.application.autoapprove" name="app.autoApprove" fieldValue="true" />
							</#if>
							
							<@s.textfield key="label.application.redirecturi" name="app.registeredRedirectUris" value="${redirectUri!}" />
							
							<@s.submit cssClass="btn btn-primary col-md-3" value="%{getText('button.main.save')}" />
						</@s.form>
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<#if (app.id!'')?trim != ''>
				<div class="box box-success">
					<div class="box-body">
						<@s.form theme="bootstrap" action="${request.contextPath}${request.servletPath}/../secret">
							<@s.hidden name="id" value="%{app.id}" />
							<@s.textfield key="label.application.secret" name="app.secret" readonly="true" />
							<@s.submit cssClass="btn btn-success col-md-6" key="label.application.secret.generate" />
						</@s.form>
					</div>
				</div>
				</#if>
			</div>
		</div>
	</body>
</html>