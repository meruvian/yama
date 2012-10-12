<ul class="nav">
<#if request.session.getAttribute("SPRING_SECURITY_CONTEXT")??>
<#if menus??>
<#list menus.entityList as menu>
<li class="dropdown" id="menu1">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#${menu.id}">
      ${menu.name!}
      <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
    	<#list menu.moduleFunctions as submenu>
    		<li><a href="<@s.url value="${submenu.moduleUrl!}"/>">${submenu.name!}</a></li>
    	</#list>
    </ul>
</li>
</#list>
</#if>
</#if>
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
   		Category
     	<b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
    	<li><a href="<@s.url value="/backend/news"/>"/>News</a></li>  
    	<li><a href="<@s.url value="/backend/article"/>"/>Article</a></li>    
    </ul>
</li>
</ul>

<ul class="nav pull-right">
<#if request.session.getAttribute("SPRING_SECURITY_CONTEXT")??>
<li>
	<#if request.session.getAttribute("cimandeSecurityUser")??>
	<#assign name = request.session.getAttribute("cimandeSecurityUser").name />								
		<a title="Profil" href="<@s.url value="/module/profile"/>"><i class="icon-user icon-white"></i> &nbsp; ${name.first} ${name.last}</a>
	</#if>
</li>
<li>
	<a title="Keluar" href="<@s.url value="/logout" />">
		<i class="icon-off icon-white"></i>&nbsp;
	</a>
</li>
<li>
	<a href="<@s.url value="/"/>">&nbsp;<i class="icon-home icon-white"></i></a>
</li>
</#if>
</ul>