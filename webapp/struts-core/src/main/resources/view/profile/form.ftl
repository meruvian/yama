<@s.form action="/module/profile/save" cssClass="form-horizontal" theme="bootstrap">
	<font color="#C24E4E">${notifEmail}</font>
	<div class="row-fluid">
	<div class="span6">
	<h3><@s.text name="user.signup.personal_information" /></h3>
	<@s.textfield key="user.signup.email" name="user.email" cssClass="span8" required="true" value="${user.email!}"/>
	<@s.textfield key="user.signup.firstname" name="user.name.first" cssClass="span8" required="true" value="${user.name.first!}"/>
	<@s.textfield key="user.signup.lastname" name="user.name.last" cssClass="span8" value="${user.name.last!}"/>
	<@s.textfield key="user.signup.address" name="user.address.street1" cssClass="span9" value="${user.address.street1!}"/>
	<@s.textfield name="user.address.street2" cssClass="span9" value="${user.address.street2!}"/>
	<@s.textfield key="user.signup.city" name="user.address.city" cssClass="span9" value="${user.address.city!}"/>
	<@s.textfield key="user.signup.zip" name="user.address.zip" cssClass="span9" value="${user.address.zip!}"/>
		
	<div class="control-group">
		<div class="controls">
			<input type="submit" class="btn btn-primary" value="<@s.text name="Save" />"/>
		</div>
	</div>
	
	</div>
	<div class="span2">
	<br/><br/><br/>
	<a href="/module/profile/edit" class="btn btn-primary">Edit Profile</a><br/><br/>
	<a href="/module/profile/change" class="btn btn-primary">Change Password</a>
	</div>
	</div>
</@s.form>
<script type="text/javascript" src="<@s.url value="/scripts/bootstrap/bootstrap-datepicker.js" />"></script>
<link rel="stylesheet" type="text/css" href="<@s.url value="/styles/datepicker.css" />" />
<script type="text/javascript">
$(function() {
	$('.datepicker').datepicker({
		autoclose : true,
		startView : 0
	});
});
</script>