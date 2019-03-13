<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showPost.ftl" as showPost>
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<@showPost.show post "post" />
		<#list comments as comment>
			<#include "parts/showLists/showComment.ftl">
		<#else>
			<h2 class="display-4 mt-3" align="center">No comments...</h2>
		</#list>
	<div class="mt-3">
		<#include "parts/forms/addCommentForm.ftl">
	</div>
</div>

</@shell.htmlPage>