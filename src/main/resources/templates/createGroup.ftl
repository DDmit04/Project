<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage> 

<#include "/parts/security.ftl">

<div class="card shadow col-8">
	<div class="card-body">
		<div class="text-center card-text">
			<#include "parts/forms/createGroupForm.ftl">
		</div>
	</div>
</div>

</@shell.htmlPage>