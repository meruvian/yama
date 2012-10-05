<div class="hero-unit">
	<h1>Welcome <#if request.session.getAttribute("cimandeSecurityUser")??>
					<#assign name = request.session.getAttribute("cimandeSecurityUser").name />								
						${name.first} ${name.last}
				</#if> to Yama</h1>
</div>