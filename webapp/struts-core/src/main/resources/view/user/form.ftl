
<@s.form action="/backend/user/add" cssClass="form-horizontal" theme="bootstrap">	
	<@s.hidden name="user.id" /> 
	
	<@s.textfield key="user.username" name="user.username" cssClass="span3" required="true" />
	<@s.password key="user.password" name="user.password" cssClass="span3" required="true" />
	<@s.textfield key="user.first" name="user.name.first" cssClass="span3" required="true" />
	<@s.textfield key="user.last" name="user.name.last" cssClass="span3" required="true" />
	<#--
	<div class="control-group">
		<label class="control-label" for="company-id">Company</label>
		<div class="controls">
			<div class="input-append">
				<input type="hidden" value="<#if user.company??>${user.company.id!}</#if>" id="company-id" name="user.company.id" />
				<input type="text" value="<#if user.company??>${user.company.name!}</#if>" readonly="readonly" id="company-name" class="span2" /><button class="btn openpopup" type="button" title="Company" object-name="companies|name" field-target="company-id|company-name" href="<@s.url value="/backend/company" />">Choose</button>
			</div>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="job-id">Position</label>
		<div class="controls">
			<div class="input-append">
				<input type="hidden" value="<#if user.job??>${user.job.id!}</#if>" id="job-id" name="user.job.id" />
				<input type="text" value="<#if user.job??>${user.job.name!}</#if>" readonly="readonly" id="job-name" class="span2" /><button class="btn openpopup" type="button" title="Position" object-name="jobDescs|name" field-target="job-id|job-name" href="<@s.url value="/backend/jobdesc" />">Choose</button>
			</div>
		</div>
	</div>
	-->
	<#--
	<@s.textfield label="Company" id="companyName" value="%{user.company.name}" required="true" name="company" cssClass="span3" onfocus="javascript:document.getElementById('companyId').click()" readonly="true" />
	<@s.textfield label="Position" id="jobName" value="%{user.job.name}" required="true" name="job" cssClass="span3" onfocus="javascript:document.getElementById('jobId').click()" readonly="true"/>
	-->	
	<@s.textfield key="user.email" name="user.email" cssClass="span3" />
	<@s.textfield key="user.address" name="user.address.street1" cssClass="span4" />
	<@s.textfield name="user.address.street2" cssClass="span4" />
	<@s.textfield key="user.city" name="user.address.city" cssClass="span3" />
	<@s.textfield key="user.state" name="user.address.state" cssClass="span3" />
	<@s.textfield key="user.zip" name="user.address.zip" cssClass="span3" />
	<@s.textfield key="user.description" name="user.description" cssClass="span4" />
	<@s.textfield key="user.type" name="user.workspaceType" cssClass="span3" />

	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<input type="reset" class="btn" value="Reset" />
	</div>
</@s.form>