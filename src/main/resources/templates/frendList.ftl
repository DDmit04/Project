<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<ul>
<#list frends as frend>
	<li class="list-group-item">
		<div>${frend.id}</div>
		<div>${frend.username}</div>
		<div>${frend.registrationDate}</div>
	</li>
<#else>
	no frends
</#list>
</ul>

</@shell.htmlPage> 
