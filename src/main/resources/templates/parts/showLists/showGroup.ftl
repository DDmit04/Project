<#macro groupList groups >



<#list groups as group>
	<ul class="list-group">
	  <li class="list-group-item d-flex justify-content-between align-items-center" style="border-left: none;
	 																					   border-right: none;
	 																					   border-top: none">
		   <div class="border-secondary media">
				<a class="mt-2" href="/groups/${group.id}"> 
					<#if group.groupPicName??>
						<img class="mr-3 rounded-circle border border-secondary" src="/imgGroupPic/${group.groupPicName}" width="64" height="64">
					<#else>
						<img class="mr-3 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="64" height="64">
					</#if>
				</a>
				<div class="media-body">
					<a href="/groups/${group.id}" class="h4 ml-2">${group.groupName}</a>
					<div class="ml-2">
						${group.groupTitle}
					</div>
					<div class="ml-2">
					   	${group.subCount} subscribers
					</div>
				</div>
			</div>
		</li>
	</ul>
<#else>
	<h4 class="display-4 ml-2 " align="left">No groups :(</h4>
</#list>

</#macro>