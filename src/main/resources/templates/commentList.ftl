<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<#include "parts/posts/showPost.ftl">
	<div class="col-8 mt-3 ">
		<ul class="list-group list-group-flush shadow border border-secondary">
		  <li class="list-group-item">comment</li>
		</ul>
	</div>
</div>
</@shell.htmlPage>