<#assign

	known = Session.SPRING_SECURITY_CONTEXT??
    isEdit = springMacroRequestContext.requestUri?contains("/edit")
>

<#if known>
	<#assign 
		currentUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
		currentUsername = currentUser.getUsername()
		currentUserId = currentUser.getId()
	>
</#if>

