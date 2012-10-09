<@s.form action="/user/signup" cssClass="form-horizontal" theme="bootstrap">
	<div class="row-fluid">
	<div class="span6">
	<h3><@s.text name="user.signup.personal_information" /></h3>
	<div class="control-group">
		<label class="control-label">Email : </label>
		<div class="controls">${user.email!}</div>
	</div>
	<div class="control-group">
		<label class="control-label">First Name : </label>
		<div class="controls">${user.name.first!}</div>
	</div>
	<div class="control-group">
		<label class="control-label">Last Name : </label>
		<div class="controls">${user.name.last!}</div>
	</div>
	<div class="control-group">
		<label class="control-label">Address : </label>
		<div class="controls">${user.address.street1!}</div>
	</div>
		<div class="control-group">
		<label class="control-label"> : </label>
		<div class="controls">${user.address.street2!}</div>
	</div>
	<div class="control-group">
		<label class="control-label">City : </label>
		<div class="controls">${user.address.city!}</div>
	</div>
	<div class="control-group">
		<label class="control-label">Zip : </label>
		<div class="controls">${user.address.zip!}</div>
	</div>
	</div>
	<div class="span2">
	<br/><br/><br/>
	<a href="/module/profile/edit" class="btn btn-primary">Edit Profile</a><br/><br/>
	<a href="/module/profile/change" class="btn btn-primary">Change Password</a>
	</div>
	</div>
</@s.form>