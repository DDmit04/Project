<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showPost.ftl" as showPost> 
<@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-md-9">
	<@showPost.show post 0 "post" />
	<#include "/parts/showLists/showComment.ftl">
	<#include "/parts/forms/addCommentForm.ftl">
</div>

</@shell.htmlPage>