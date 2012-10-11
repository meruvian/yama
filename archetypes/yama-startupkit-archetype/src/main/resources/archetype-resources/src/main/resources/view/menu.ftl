<li class="active">
	<a href='<@s.url value="/home" />'>
		<i class="icon-home icon-white"></i> Home
	</a>
</li>
<#if request.session.getAttribute("SPRING_SECURITY_CONTEXT")??>
<li>
	<a href='#/security/password'>Change Password</a>
</li>
<li>
	<a href="<@s.url value="/logout" />">
		<@s.text name="frontent.logout.text" />
	</a>
</li>
</#if>