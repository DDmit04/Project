<#import "/parts/utils/showPics.ftl" as picture>

<#include "/parts/security.ftl">

<ul class="list-group">
	<li class="list-group-item d-flex justify-content-between align-items-center" style="border-left: none;
 																						 border-right: none;
 																						 border-top: none">
		<div class="border-secondary media">
			<@picture.pic user "smallPic" "user" />
			<div class="media-body">
				<a href="/${user.id}/profile" class="h6 ml-2">${user.username}</a>
			</div>
		</div>
		<#if group??>
			<#if group.groupOwner == currentUser && currentUser != user>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<#if groupAdmins?seq_contains(user)>
							<a class="dropdown-item" href="/groups/${group.id}/${user.id}/removeAdmin">remove admin</a> 
							<a class="dropdown-item" href="/groups/${group.id}/${user.id}/makeOwner">make owner</a>
						<#else>
							<a class="dropdown-item" href="/groups/${group.id}/${user.id}/addAdmin">give admin</a>
							<#if banList?seq_contains(user)>
								<a class="dropdown-item" href="/groups/${group.id}/${user.id}/unban">unban</a>
							<#else>
								<a class="dropdown-item" href="/groups/${group.id}/${user.id}/ban">ban</a>
							</#if>
						</#if>
					</div>
				</div>
			</#if>
			<#if group.groupOwner != currentUser && currentUser == user>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<#if groupAdmins?seq_contains(currentUser)>
<!-- 						modal appears in groupConnections -->
							<a class="dropdown-item" data-toggle="modal" data-target="#takeOfAdmin">take off admin</a> 
						</#if>
						
					</div>
				</div>
			</#if>
		</#if>
	</li>
</ul>
