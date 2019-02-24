<#assign

	known = Session.SPRING_SECURITY_CONTEXT??
    isEdit = springMacroRequestContext.requestUri?contains("/edit")
>

<#if known>
	<#assign 
		user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
		username = user.getUsername()
	>
</#if>

