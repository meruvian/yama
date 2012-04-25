<header>
	<h1>Login</h1>
</header>
<div class="row">
	<div class="span12">
		<@s.form theme="bootstrap" action="/login" cssClass="form-horizontal">
		<@s.actionmessage theme="bootstrap" />
		<@s.textfield label="Username" name="username" cssClass="span3" /> 
		<@s.password label="Password" name="password" cssClass="span3" />
		<div class="form-actions" style="background-color: transparent;">
			<button value="Save" class="btn btn-primary">Login</button>
		</div>
		</@s.form>
	</div>
</div>