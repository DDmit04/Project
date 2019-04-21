<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage>

<#include "parts/security.ftl">

<#if loginAttention??>
	<div class="alert alert-warning col-8" role="alert">
		${loginAttention}
	</div>
</#if>
<div class="col-8">
	<a href="/login" role="button" class="btn btn-primary btn-lg btn-block"> go to login</a>
</div>

</@shell.htmlPage>