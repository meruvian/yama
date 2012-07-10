<@s.form id="change-password" theme="bootstrap" cssClass="form-horizontal">
	<@s.password id="newpass1" label="New Password" name="password" tooltip="Enter your new password" placeholder="new password..." />
	<@s.password id="newpass2" label="Confirm password" tooltip="Confirm password" placeholder="confirm password..." />
	
	<div class="form-actions">
		<input type="submit" value="Save" class="btn btn-primary" style="width: 100px;" />
		<input type="reset" value="Reset" class="btn" style="width: 100px;" />
		<span id="alert" class="alert alert-error hide"><@s.text name="change.password.doesntmatch" /></span>
	</div>
</@s.form>

<script type="text/javascript">
$(function() {
	$('#newpass1, #newpass2').keyup(function() {
		if (!($('#newpass1').val() === $('#newpass2').val())) {
			$('#alert').fadeIn();
		} else {
			$('#alert').fadeOut();
		}
	});
});
</script>