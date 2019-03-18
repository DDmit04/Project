<#macro groupList group picWidth picHeight >

<ul class="list-group">
  <li class="list-group-item d-flex justify-content-between align-items-center" style="border-left: none;
 																					   border-right: none;
 																					   border-top: none">
	   <div class="border-secondary media">
			<a href="/groups/${group.id}"> 
				<#if group.groupPicName??>
					<img class="mr-2 rounded-circle border border-secondary" src="/imgGroupPic/${group.groupPicName}" width="${picWidth}" height="${picHeight}" class="mr-3">
				<#else>
					<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="${picWidth}" height="${picHeight}" class="mr-3">
				</#if>
			</a>
			<div class="media-body">
				<a href="/groups/${group.id}" class="h6 ml-2">${group.groupName}</a>
			</div>
		</div>
	</li>
</ul>

</#macro>