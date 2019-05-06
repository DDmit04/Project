<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage> 

<#include "/parts/security.ftl">

<div class="card shadow col-8">
	<div class="card-body">
		<div class="text-center card-text">
			<#if creationTarget == "group">
				<#include "/parts/forms/createGroupForm.ftl">
			<#elseif creationTarget == "chat">
				<#include "/parts/forms/createChatForm.ftl">
			</#if>
		</div>
	</div>
</div>

</@shell.htmlPage>
