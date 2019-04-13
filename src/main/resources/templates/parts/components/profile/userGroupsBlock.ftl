<!-- parts/components/profile/noAccesProfile  -->

<div class="card border-secondary shadow mt-3" style="height: 300px; width: 280px;">
	<div class="card-body">
		<a href="/${user.id}/profile/socialList/groups">
			user Groups:(${user.groupSubscriptionsCount})
		</a>
		<div class="ml-2 mt-2">
			<#assign i = 0>
			<#list userGroups as group>
				<#assign i += 1>
				<a href="/groups/${group.id}">
					<#if group.groupPicName??>
						<img class="mx-1 mt-4 rounded-circle border border-secondary" src="/imgGroupPic/${group.groupPicName}" width="50" height="50" alt="...">
					<#else>
					  	<img class="mx-1 mt-4 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="50" height="50" alt="...">
					 </#if>	
				</a>
				<#if i == 9> <#break> </#if>
			</#list>
		</div>
	</div>
</div>