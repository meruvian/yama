<#if success??>
<div class="alert">
	<a class="close" data-dismiss="alert" href="#">&times;</a>
	Module function successfully added to role!
</div>
</#if>

<@s.form action="/backend/user_privilege/add" cssClass="form-horizontal" theme="bootstrap">
	<@s.hidden name="user.id"/>	
	<@s.textfield label="Username" name="user.username" cssClass="span3" readonly="true" />
	<div class="control-group">
		<label class="control-label" for="company-id">Role</label>
		<div class="controls">
			<div class="input-append">
				<input type="hidden" id="role-id" name="role.id" />
				<input type="text" readonly="readonly" id="role-name" class="span2" name="role.name"/><button class="btn openpopup" type="button" title="Role Function" object-name="roles|name" field-target="role-id|role-name" href="<@s.url value="/backend/user_privilege/role/list/${user.id}" />">Choose</button>
			</div>
		</div>
	</div>
	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<a class="btn" href="/backend/user" style="width: 80px;">Cancel</a>
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
		<#list roles.entityList as c>
		<tr>
			<td>${no}</td>
			<td>${c.name!}</td>
			<td>${c.description!}</td>
			<td>
				<a class="btn confirm" href="<@s.url value="/backend/user_privilege/role/delete/${user.id}/${c.id}" />" title="Remove" data-message="Are you sure want to remove ${c.name!}?">
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
	<a class="btn disabled">${page} of ${roles.totalPage}</a>
	<#if page &lt; roles.totalPage>
	<a class="btn" href="${request.servletPath}?max=${max}&page=${page + 1}">&nbsp;<i class="icon-forward"></i></a>
	<a class="btn" href="${request.servletPath}?max=${max}&page=${roles.totalPage}">&nbsp;<i class="icon-fast-forward"></i></a>
	</#if>
</div>