<div class="row">
<form method="get" class="form-inline span12">
	<input type="text" name="q" value="${q!}" class="span6" placeholder="Search..." />
	<input type="submit" value="Search" class="span2 btn" />
	<a class="btn btn-primary pull-right" href="<@s.url value="${request.servletPath}/add"/>">New User</a>
</form>
</div>

<#if success??>
<div class="alert fade in">
	<a class="close" data-dismiss="alert" href="#">&times;</a>
	Company successfully added!
</div>
</#if>
<table class="table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<th>#</th>
			<th>LOGIN</th>
			<th class="span4">NAME</th>
			<th>EMAIL</th>
			<th>ACTIONS</th>
		</tr>
	</thead>
	<tbody>
		<#assign no = (max * page) - max + 1>
		<#list users.entityList as c>
		<tr>
			<td>${no}</td>
			<td>${c.username!}</td>			
			<td>${c.name.first!} ${c.name.last!}</td>
			<td>${c.email!}</td>
			<td>
				<a class="btn" title="Edit" href="<@s.url value="${request.servletPath}/edit/${c.id!}"/>"><i class="icon-edit"></i></a>
				<a class="btn" title="Privileges" href="<@s.url value="/backend/user_privilege/edit/${c.id!}"/>"><i class="icon-th-list"></i></a>
				<a class="btn confirm" title="Delete" href="<@s.url value="${request.servletPath}/delete/${c.id!}"/>" data-message="Are you sure want to delete ${c.name.first!} ${c.name.last!}?"><i class="icon-trash"></i></a>
			</td>
		</tr>
		<#assign no = no + 1>
		</#list>
	</tbody>
</table>
<span class="label pull-left">Found ${users.rowCount} row(s)</span>
<div class="btn-group pull-right">
	<#if page &gt; 1>
	<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=1"/>"><i class="icon-fast-backward"></i>&nbsp;</a>
	<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=${page - 1}"/>"><i class="icon-backward"></i>&nbsp;</a>
	</#if>
	<a class="btn disabled">${page} of ${users.totalPage}</a>
	<#if page &lt; users.totalPage>
	<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=${page + 1}"/>">&nbsp;<i class="icon-forward"></i></a>
	<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=${users.totalPage}"/>">&nbsp;<i class="icon-fast-forward"></i></a>
	</#if>
</div>