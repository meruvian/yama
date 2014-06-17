<html>
	<head>
		<title><@s.text name="page.admin.user.title" /></title>
	</head>
	<body>
		<content tag="header"><@s.text name="page.admin.user.header" /></content>
		<content tag="script">
		<script type="text/javascript">
		$(function() {
			var roles = [];
			<#if userRoles??>
			<#list userRoles.content as r>
			roles.push('${(r.name!"")?upper_case}');
			</#list>
			</#if>
			
			for (r in roles) {
				$('select[name="roles"] option[value="' + roles[r] + '"]').attr('selected', true);
			}
		});
		</script>
		</content>

		<@s.actionerror theme="bootstrap"/>
		<@s.actionmessage theme="bootstrap"/>
		<div class="row">
			<div class="col-md-9">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title"><@s.text name="label.admin.user.title" /></h3>
					</div>
					
					<div class="box-body">
						<@s.form theme="bootstrap">
							<@s.hidden name="user.id" />
							<@s.textfield key="label.admin.user.username" name="user.username" />
							<div class="row">
								<div class="col-md-6">
									<@s.password key="label.admin.user.password" name="user.password" />
								</div>
								<div class="col-md-6">
									<@s.password key="label.admin.user.password.confirm" name="confirmPassword" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<@s.textfield key="label.admin.user.name.first" name="user.name.first" />
								</div>
								<div class="col-md-6">
									<@s.textfield key="label.admin.user.name.last" name="user.name.last" />
								</div>
							</div>
							<@s.textfield key="label.admin.user.email" name="user.email" />
							
							<div class="form-group ">
								<label for="roles"><@s.text name="label.admin.user.roles" /></label>
								<div class="controls">
									<select name="roles" class="form-control" multiple="true">
										<#list roles.content as r>
										<option value="${(r.name!"")?upper_case}">${(r.name!"")?upper_case}</option>
										</#list>
									</select>
								</div>
							</div>
							<@s.submit cssClass="btn btn-primary col-md-3" value="%{getText('button.main.save')}" />
						</@s.form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>