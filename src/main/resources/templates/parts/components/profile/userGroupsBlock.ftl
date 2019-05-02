<!-- parts/components/profile/noAccesProfile  -->

<#include "/parts/security.ftl">

<div class="card border-secondary shadow mt-3" style="height: 300px; width: 280px;">
	<div class="card-body">
		<a href="/${user.id}/profile/socialList/groups">
			<#if user.username == currentUsername>
				my
			<#else>
				user
			</#if>
			Groups:(${user.groupSubscriptionsCount})
		</a>
		<div class="ml-2 mt-2">
			<#assign i = 0>
			<#list userGroups as group>
				<#assign i += 1>
				<a href="/groups/${group.id}">
					<#if group.groupPicName??>
						<img class="mx-1 mt-4 rounded-circle border border-secondary" src="/imgGroupPic/${group.groupPicName}" width="50" height="50" alt="...">
					<#else>
					  	<img class="mx-1 mt-4 rounded-circle border border-secondary" src="http://localhost:8080/static/images/defaultGroupPic.png" width="50" height="50" alt="...">
					 </#if>	
				</a>
				<#if i == 9> <#break> </#if>
			</#list>
		</div>
	</div>
</div>