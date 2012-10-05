<#if request.session.getAttribute("SPRING_SECURITY_CONTEXT")??>
	<#include "/view/include/home_module.ftl" />
<#else>
	<#include "/view/include/home_button.ftl" />
</#if>