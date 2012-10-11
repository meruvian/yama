<@s.form action="/backend/workflow_role/add" cssClass="form-horizontal" theme="bootstrap">	
	<@s.hidden name="role.id" /> 
	
	<@s.textfield key="role.name" name="role.name" cssClass="span3" required="true" />
	<@s.textfield key="role.description" name="role.description" cssClass="span4" />
	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<input type="reset" class="btn" value="Reset" />
	</div>
</@s.form>

