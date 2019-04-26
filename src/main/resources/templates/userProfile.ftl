<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/utils/showPics.ftl" as picture>
<@shell.htmlPage> 

<#include "parts/security.ftl">

<#if user.bloked>
	<#include "/parts/components/profile/noAccesProfile.ftl">
<#else>
	<#include "/parts/components/profile/accesProfile.ftl">
</#if>

</@shell.htmlPage>