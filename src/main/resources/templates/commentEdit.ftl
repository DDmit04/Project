<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showPost.ftl" as showPost>
<#import "parts/showLists/showImageFull.ftl" as showImage>  
<@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-md-9">
	<#if post??>
		<@showPost.show post 0 "post" />
	<#elseif image??>
		<#include "/parts/showLists/showImageFull.ftl">
	</#if>
	<#include "/parts/showLists/showComment.ftl">
	<#include "/parts/forms/addCommentForm.ftl">
</div>

</@shell.htmlPage>