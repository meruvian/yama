<@s.form action="/module/profile/savepass" cssClass="form-horizontal" theme="bootstrap">
	<div class="row-fluid">
	<div class="span6">
	
	<h3><@s.text name="user.signup.personal_information" /></h3>
	<@s.password key="user.signup.new_password" id="pass" name="user.password" cssClass="span6" required="true"/>
	<@s.password key="user.signup.confrim_password" id="cpass" name="cpass" cssClass="span6" />
	<div class="control-group">
		<div class="controls">
			<input type="submit" class="btn btn-primary" value="<@s.text name="Simpan" />"/>
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
<script type="text/javascript">
$('#cpass').keyup(function() {
		var password = $("#pass").val();
	var confirmPassword = $("#cpass").val();

	if (password != confirmPassword){
	$("#txtcheck").remove();
	$("#cpass").after("<font color=\"#C24E4E\" id=\"txtcheck\">&nbsp;&nbsp;Passwords do not match!</font>");
	}else{
	$("#txtcheck").remove();
    $("#cpass").after("<font color=\"green\" id=\"txtcheck\">&nbsp;&nbsp;Passwords match.</font>");
    }
});
</script>