<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage> 

<div class="col-md-9">
	<#include "parts/forms/addPostForm.ftl">
	<#include "/parts/showLists/showListsOfPosts.ftl">
</div>

</@shell.htmlPage>