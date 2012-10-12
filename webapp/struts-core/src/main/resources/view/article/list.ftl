<div class="container-fluid">
	<div class="row-fluid">
		<form method="get" class="form-inline span12">
		<input type="text" name="q" value="${q!}" class="span6" placeholder="Search..." />
		<input type="submit" value="Search" class="span2 btn" />
		<a class="btn btn-primary pull-right" href="<@s.url value="${request.servletPath}/add"/>">New News</a>
		</form>
		<div id="article" class="bs-docs-example span12">
			<#list articles.entityList as a>
			<div style="background-color: #FAFAFC; margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
			  	  
				<a href="<@s.url value="${request.servletPath}/detail/${a.id!}"/>" class="title-news" style="margin: 0 0;">${a.title!}</a>
				<br>
				<font size="2"> Posted by <a href="#">${a.user.name.first!} ${a.user.name.last!}</a> on ${a.logInformation.createDate!}</font>

				<p style="text-align: justify;margin-top: 12px;">
					<div class="expander">
						${a.content!}
					</div>
				</p>
				<p align="right">
					<a class="btn" title="Edit" href="<@s.url value="${request.servletPath}/edit/${a.id!}"/>"><i class="icon-edit"></i></a>
					<a class="btn" title="Detail" href="<@s.url value="${request.servletPath}/detail/${a.id!}"/>"><i class="icon-th-list"></i></a>
					<a class="btn confirm" title="Delete" href="<@s.url value="${request.servletPath}/delete/${a.id!}"/>" data-message="Are you sure want to delete ${a.title!} ?"><i class="icon-trash"></i></a>
				</p>
				
			</div>
			</#list>
			<div style=" margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
			<span class="label pull-left">Found ${articles.rowCount} row(s)</span>
			<div class="btn-group pull-right">
				<#if page &gt; 1>
					<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=1"/>"><i class="icon-fast-backward"></i>&nbsp;</a>
					<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=${page - 1}"/>"><i class="icon-backward"></i>&nbsp;</a>
				</#if>
					<a class="btn disabled">${page} of ${articles.totalPage}</a>
				<#if page &lt; articles.totalPage>
					<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=${page + 1}"/>">&nbsp;<i class="icon-forward"></i></a>
					<a class="btn" href="<@s.url value="${request.servletPath}?max=${max}&page=${articles.totalPage}"/>">&nbsp;<i class="icon-fast-forward"></i></a>
				</#if>
			</div>
			</div>
		</div>
		

	</div>
</div>
<script>
$(document).ready(function() {
  var opts = {collapseTimer: 4000};
  $('div.expander').expander();
});

</script>
