<#assign

	known = Session.SPRING_SECURITY_CONTEXT??
    isEdit = springMacroRequestContext.requestUri?contains("/edit")
>

<!--             user = Session.SPRING_SECURITY_CONTEXT.authentication.principal -->

