<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showGroup.ftl" as groupList> 
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<#if user??>
	<h3>
		<#if currentUsername != user.username>
			${user.username}'s groups (${user.groupSubscriptionsCount})
		<#else>
			My groups (${user.groupSubscriptionsCount})
		</#if>
	</h3>
</#if>

<div class="col-9">
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade show active" role="tabpanel" style="padding: 15px 5px;">
			<@groupList.groupList groups />	
		</div>
	</div>
</div>
</@shell.htmlPage>