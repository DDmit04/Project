<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/utils/showPics.ftl" as picture>
<#import "parts/showLists/showProfile.ftl" as prof>
<@shell.htmlPage> 

<#include "parts/security.ftl">

<#if user.isBloked>
	<@prof.profile "blackListed" />
<#else>
	<@prof.profile "full" />
</#if>


</@shell.htmlPage>