<div>
	<div>
		<div id="login-dialog" class="modal" style="position: relative; top: auto; left: auto; margin: 0 auto; z-index: 1; max-width: 100%;">
			<div class="modal-header">
				<h2><@s.text name="frontend.login.text" /></h2>
			</div>
			<div class="modal-body">
				<@s.form id="login-form" theme="bootstrap" action="/auth" cssClass="form-horizontal">
					<#if request.getParameter("error")??>
						<p class="alert alert-error"><@s.text name="frontend.login.failed" /></p>
					</#if>
					<@s.actionmessage theme="bootstrap" />
					<@s.textfield key="frontend.username.text" name="username" cssClass="span3" /> 
					<@s.password key="frontend.password.text" name="password" cssClass="span3" />
					<input type="hidden" name="redirectUri" value="<@s.property value="%{#parameters.redirect_uri}" />" />
					<input type="submit" value="login" class="btn btn-primary" style="position: absolute; left: -9999px; width: 1px; height: 1px;" />
				</@s.form>
			</div>
			<div class="modal-footer">
				<a href="<@s.url value="/security/password/forgot" />" style="float: left; text-decoration: none; line-height: 30px;">
					<@s.text name="user.signup.forgot_password" /> 
				</a>
                
				<button value="Save" id="login-btn" class="btn btn-primary"><@s.text name="button.login" /></button>
				<#--<a href="#" id="fblogin" class="btn btn-primary" style="background-color: #0074CC; background-image: -ms-linear-gradient(top,#08C,#05C); background-image: -webkit-gradient(linear,0 0,0 100%,from(#08C),to(#05C)); background-image: -webkit-linear-gradient(top,#08C,#05C); background-image: -o-linear-gradient(top,#08C,#05C); background-image: -moz-linear-gradient(top,#08C,#05C); background-image: linear-gradient(top,#08C,#05C); background-repeat: repeat-x;" title="Masuk dengan akun Facebook">
					
						<img src="<@s.url value="/images/fb-logo.jpg" />" alt="Login with Facebook Account" style="max-width: 30%; height: auto; border: 0; padding-left: 10px;" />
					
					&nbsp;<i class="icon-facebook icon-white"></i>&nbsp;
				</a>
				-->
				<form name="socialform" method="post" id="socialform"></form>
			</div>
		</div>
	</div>
</div>
<form name="socialform" action="/backend/user/login/facebook" method="post">
</form>
<script type="text/javascript">
$(function() {
	$('#fblogin').click(function(){
		$('#socialform').attr('action', '/backend/user/login/facebook');
		$('#socialform').submit();
		return false;
	});
	$('#login-btn').click(function() { $('#login-form').submit(); });
	$('input[name=username]').focus();
});
</script>