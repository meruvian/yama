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
    		<li><a href="${submenu.moduleUrl!}">${submenu.name!}</a></li>
    	</#list>
    </ul>
</li>
</#list>
</#if>
</#if>
</ul>

<ul class="nav pull-right">
<#if request.session.getAttribute("SPRING_SECURITY_CONTEXT")??>
<li>
	<#if request.session.getAttribute("cimandeSecurityUser")??>
	<#assign name = request.session.getAttribute("cimandeSecurityUser").name />								
		<a title="Profil" href="#"><i class="icon-user icon-white"></i> &nbsp; ${name.first} ${name.last}</a>
	</#if>
</li>
<li>
	<a title="Keluar" href="<@s.url value="/logout" />">
		<i class="icon-off icon-white"></i>&nbsp;
	</a>
</li>
<li>
	<a href="/">&nbsp;<i class="icon-home icon-white"></i></a>
</li>
</#if>
</ul>