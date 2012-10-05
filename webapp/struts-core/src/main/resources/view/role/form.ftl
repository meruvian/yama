<@s.form action="/backend/workflow_role/add" cssClass="form-horizontal" theme="bootstrap">	
	<@s.hidden name="role.id" /> 
	
	<@s.textfield key="role.name" name="role.name" cssClass="span3" required="true" />
	<@s.textfield key="role.description" name="role.description" cssClass="span4" />
	<!--  <div class="control-group">
		<label class="control-label" for="master-id">Master :</label>
		<div class="controls">
			<div class="input-append">
				<@s.hidden id="master-id" name="role.master.id" />
				<#if role.id??>
					<#assign masterUrl = '/master/' + role.id />
				<#else>
					<#assign masterUrl = ''/>
				</#if>
				<input type="text" value="<#if role.master??>${role.master.name!'None'}</#if>" readonly="readonly" id="master-name" class="span2" /><button class="btn openpopup" type="button" title="Master Role" object-name="roles|name" field-target="master-id|master-name" href="<@s.url value="/backend/workflow_role" />${masterUrl}">Choose</button>
			</div>
		</div>
	</div>-->
	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<input type="reset" class="btn" value="Reset" />
	</div>
</@s.form>

<#if role.id??>
<h3>Nodes</h3>
<table class="table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<th>#</th>
			<th>NAME</th>
			<th>DESCRIPTION</th>
			<th>ACTIONS</th>
		</tr>
	</thead>
	<tbody>
		<#assign no = 1 * page>
		<#list role.roles as c>
		<tr>
			<td>${no}</td>
			<td>${c.name!}</td>
			<td>${c.description!}</td>
			<td>
				<a class="btn" title="Edit" href="${c.id!}"><i class="icon-edit"></i></a>
			</td>
		</tr>
		<#assign no = no + 1>
		</#list>
	</tbody>
</table>
</#if>