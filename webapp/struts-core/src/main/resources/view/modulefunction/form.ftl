<@s.form action="/backend/module_function/add" cssClass="form-horizontal" theme="bootstrap">	
	<@s.hidden name="moduleFunction.id" /> 
	
	<@s.textfield key="function.name" name="moduleFunction.name" cssClass="span3" required="true"/>
	<@s.textfield key="function.description" name="moduleFunction.description" cssClass="span4" />
	<#if moduleFunction.master??>
	<@s.textfield key="function.module" name="moduleFunction.moduleUrl" required="true" value="${moduleFunction.moduleUrl.replace(\" ${moduleFunction.master.moduleUrl} \", \"\")}" cssClass="span3" />
	<#else>
	<@s.textfield key="function.module" name="moduleFunction.moduleUrl" required="true" cssClass="span3" />
	</#if>
	
	<div class="control-group">
		<label class="control-label" for="master-id">Master :</label>
		<div class="controls">
			<div class="input-append">
				<#--
				<input type="hidden" id="master-id" name="moduleFunction.master.id" value="${moduleFunction}" />
				-->
				<@s.hidden id="master-id" name="moduleFunction.master.id" />
				<#if moduleFunction.id??>
					<#assign masterUrl = '/master/' + moduleFunction.id />
				<#else>
					<#assign masterUrl = ''/>
				</#if>
				<input type="text" value="<#if moduleFunction.master??>${moduleFunction.master.name!'None'}</#if>" readonly="readonly" id="master-name" class="span2" /><button class="btn openpopup" type="button" title="Master Module" object-name="moduleFunctions|name" field-target="master-id|master-name" href="<@s.url value="/backend/module_function" />${masterUrl}">Choose</button>
			</div>
		</div>
	</div>
	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<input type="reset" class="btn" value="Reset" />
	</div>
</@s.form>

<#if moduleFunction.id??>
<h3>Nodes</h3>
<table class="table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<th>#</th>
			<th>NAME</th>
			<th>DESCRIPTION</th>
			<th>URL</th>
			<th>ACTIONS</th>
		</tr>
	</thead>
	<tbody>
		<#assign no = 1 * page>
		<#list moduleFunction.moduleFunctions as c>
		<tr>
			<td>${no}</td>
			<td>${c.name!}</td>
			<td>${c.description!}</td>
			<td>${c.moduleUrl!}</td>
			<td>
				<a class="btn" title="Edit" href="${c.id!}"><i class="icon-edit"></i></a>
			</td>
		</tr>
		<#assign no = no + 1>
		</#list>
	</tbody>
</table>
</#if>