<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#list groups as group>
	<#include "parts/showLists/showGroup.ftl">
<#else>
	no
</#list>

</@shell.htmlPage>