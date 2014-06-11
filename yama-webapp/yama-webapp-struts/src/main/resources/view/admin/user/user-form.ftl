<html>
	<head>
		<title>User</title>
	</head>
	<body>
		<content tag="header">User</content>
		<content tag="headerDetail">Add New Users</content>
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
		<@s.fielderror theme="bootstrap"/>
		<div class="row">
			<div class="col-md-9">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">User Information</h3>
					</div>
					
					<div class="box-body">
						<@s.form theme="bootstrap">
							<@s.hidden name="user.id" />
							<@s.textfield label="Username" name="user.username" />
							<div class="row">
								<div class="col-md-6">
									<@s.password label="Password" name="user.password" />
								</div>
								<div class="col-md-6">
									<@s.password label="Confirm Password" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<@s.textfield label="First Name" name="user.name.first" />
								</div>
								<div class="col-md-6">
									<@s.textfield label="Last Name" name="user.name.last" />
								</div>
							</div>
							<@s.textfield label="Email" name="user.email" />
							
							<div class="form-group ">
								<label for="roles">Roles</label>
								<div class="controls">
									<select name="roles" class="form-control" multiple="true">
										<#list roles.content as r>
										<option value="${(r.name!"")?upper_case}">${(r.name!"")?upper_case}</option>
										</#list>
									</select>
								</div>
							</div>
							<@s.submit cssClass="btn btn-primary col-md-3" value="Save" />
						</@s.form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>