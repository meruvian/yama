<#if success??>
<div class="alert">
	<a class="close" data-dismiss="alert" href="#">&times;</a>
	Module function successfully added to role!
</div>
</#if>

<@s.form action="/backend/role_privilege/add" cssClass="form-horizontal" theme="bootstrap">
	<@s.hidden name="role.id"/>	
	<@s.textfield label="Name" name="role.name" cssClass="span3" readonly="true" />
	<@s.textfield label="Description" name="role.description" cssClass="span4" readonly="true" />
	<div class="control-group">
		<label class="control-label" for="company-id">Module Function</label>
		<div class="controls">
			<div class="input-append">
				<input type="hidden" id="module-function-id" name="moduleFunction.id" />
				<input type="text" readonly="readonly" id="module-function-name" class="span2" name="moduleFunction.name"/><button class="btn openpopup" type="button" title="Module Function" object-name="moduleFunctions|name" field-target="module-function-id|module-function-name" href="<@s.url value="/backend/role_privilege/mf/list/%{role.id}" />">Choose</button>
			</div>
		</div>
	</div>
	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<a class="btn" href="/backend/workflow_role" style="width: 80px;">Cancel</a>
	</div>
</@s.form>

<table class="table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<th>#</th>
			<th>NAME</th>
			<th>DESCRIPTION</th>
			<th>ACTION</th>
		</tr>
	</thead>
	<tbody>
		<#assign no = 1 * page>
		<#list moduleFunctions.entityList as c>
		<tr>
			<td>${no}</td>
			<td>${c.name!}</td>
			<td>${c.description!}</td>
			<td>
				<a class="btn confirm" href="<@s.url value="/backend/role_privilege/mf/delete/${role.id}/${c.id}" />" title="Remove" data-message="Are you sure want to remove ${c.name!}?">
					<i class="icon-trash"></i>
				</a>
			</td>
		</tr>
		<#assign no = no + 1>
		</#list>
	</tbody>
</table>
<div class="btn-group pull-right">
	<#if page &gt; 1>
	<a class="btn" href="${request.servletPath}?max=${max}&page=1"><i class="icon-fast-backward"></i>&nbsp;</a>
	<a class="btn" href="${request.servletPath}?max=${max}&page=${page - 1}"><i class="icon-backward"></i>&nbsp;</a>
	</#if>
	<a class="btn disabled">${page} of ${moduleFunctions.totalPage}</a>
	<#if page &lt; moduleFunctions.totalPage>
	<a class="btn" href="${request.servletPath}?max=${max}&page=${page + 1}">&nbsp;<i class="icon-forward"></i></a>
	<a class="btn" href="${request.servletPath}?max=${max}&page=${moduleFunctions.totalPage}">&nbsp;<i class="icon-fast-forward"></i></a>
	</#if>
</div>