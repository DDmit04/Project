<#if !isEdit>
	<#list posts as post>
		<#include "showLists/showPost.ftl">
	<#else>
		<h2 class="display-4 mt-5" align="center">Oops! Nothing here!</h2>
	</#list>
<#else>
	<#include "showLists/showPost.ftl">
</#if>