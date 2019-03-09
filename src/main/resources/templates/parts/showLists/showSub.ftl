<#macro showSub subscriptions subsType>

	<#list subscriptions as sub>
		<ul class="list-group">
		  	<li class="list-group-item d-flex justify-content-between align-items-center" style="border: 0 none;">
		   		<div class="border-secondary media">
					<a href="/${sub.id}/profile"> 
						<#if sub.userPicName??>
							<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${sub.userPicName}" width="34" height="34" class="mr-3">
						<#else>
							<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
						</#if>
					</a>
					<div class="media-body">
						<a href="/${sub.id}/profile" class="h6 ml-2">${sub.username}</a>
					</div>
				</div>
		  	</li>
		</ul>
		<hr>
	<#else>
		<#if subsType == "subscriptions">
			<h4 class="display-4 ml-2 " align="left">No subscriptions :(</h4>
		<#else>
			<h4 class="display-4 ml-2 " align="left">No subscribers :(</h4>
		</#if>
	</#list>
		
</#macro>