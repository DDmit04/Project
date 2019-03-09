<#import "showLists/postMacro.ftl" as postMacro> 

<#if !isEdit>
	<#list posts as post>
		<@postMacro.postMacro post "post" />
	<#else>
		<h2 class="display-4 mt-5" align="center">Oops! Nothing here!</h2>
	</#list>
<#else>
	<@postMacro.postMacro post "post" />
</#if>