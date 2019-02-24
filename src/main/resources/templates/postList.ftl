<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 


<#include "parts/forms/addPostForm.ftl">

<#if !isEdit>
	<#list posts as post>
		<#include "parts/posts/showPost.ftl">
	<#else>
		<h2 class="display-4 mt-5" align="center">Oops! Nothing here!</h2>
	</#list>
<#else>
	<#include "parts/posts/showPost.ftl">
</#if>

</@shell.htmlPage>