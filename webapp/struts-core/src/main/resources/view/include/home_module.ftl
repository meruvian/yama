<div class="container-fluid">
	<div class="row-fluid">
		
		<div id="newsfeed" class="bs-docs-example span9">
			
			<#list newses.entityList as n>
			<div style="background-color: #FAFAFC; margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
			  	  
				<a href="<@s.url value="${request.servletPath}/news/detail/${n.id!}"/>" class="title-news" style="margin: 0 0;">${n.title!}</a>
				<br>
				<font size="2"> Posted by <a href="#">${n.user.name.first!} ${n.user.name.last!}</a> on ${n.logInformation.createDate!}</font>

				<p style="text-align: justify;margin-top: 12px;">
					<div class="expander">
						${n.content!}
					</div>
				</p>
				
			</div>
			</#list>
			
			<div style="; margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
			 	  
				<span class="label pull-left">Found ${newses.rowCount} row(s)</span>
				<div class="btn-group pull-right">
					<#if pageNews &gt; 1>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxNews}&pageNews=1"/>"><i class="icon-fast-backward"></i>&nbsp;</a>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxNews}&pageNews=${pageNews - 1}"/>"><i class="icon-backward"></i>&nbsp;</a>
					</#if>
						<a class="btn disabled">${pageNews} of ${newses.totalPage}</a>
					<#if pageNews &lt; newses.totalPage>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxNews}&pageNews=${pageNews + 1}"/>">&nbsp;<i class="icon-forward"></i></a>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxNews}&pageNews=${newses.totalPage}"/>">&nbsp;<i class="icon-fast-forward"></i></a>
					</#if>
				</div> 
			</div>
			
		</div>
		<div id="article" class="bs-docs-example span3">
			<#list articles.entityList as a>
			<div style="background-color: #FAFAFC; margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
			  	  <a href="<@s.url value="${request.servletPath}/article/detail/${a.id!}"/>" class="" style="margin: 0 0;">${a.title!}</a>
				</div>
			</#list>
			<div style="text-align:center; margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
				<div class="btn-group">
					<#if pageArticle &gt; 1>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxArticle}&pageArticle=1"/>"><i class="icon-fast-backward"></i>&nbsp;</a>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxArticle}&pageArticle=${pageArticle - 1}"/>"><i class="icon-backward"></i>&nbsp;</a>
					</#if>
						<a class="btn disabled">${pageArticle} of ${articles.totalPage}</a>
					<#if pageArticle &lt; articles.totalPage>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxArticle}&pageArticle=${pageArticle + 1}"/>">&nbsp;<i class="icon-forward"></i></a>
						<a class="btn" href="<@s.url value="${request.servletPath}?max=${maxArticle}&pageArticle=${articles.totalPage}"/>">&nbsp;<i class="icon-fast-forward"></i></a>
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
