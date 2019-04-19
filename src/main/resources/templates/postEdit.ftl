<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showPost.ftl" as showPost> 
<@shell.htmlPage> 

<div class="col-md-9">
	<#include "parts/forms/addPostForm.ftl">
	<@showPost.show post 0 "post" />
</div>

</@shell.htmlPage>