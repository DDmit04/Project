
<ul class="list-group shadow">
	<li class="list-group-item d-flex justify-content-between align-items-center">
	   <div class="border-secondary media">
			<a href="/groups/${group.id}"> 
				<#if group.groupPicName??>
					<img class="mr-2 rounded-circle border border-secondary" src="/imgGroupPic/${group.groupPicName}" width="34" height="34" class="mr-3">
				<#else>
					<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
				</#if>
			</a>
			<div class="media-body">
				<a href="/groups/${group.id}" class="h6 ml-2">${group.groupName}</a>
			</div>
		</div>
	</li>
</ul>