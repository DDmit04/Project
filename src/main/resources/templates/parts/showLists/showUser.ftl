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
		<#else>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<#if user.username != currentUsername>
						
							<#if user.inBlackList?seq_contains(currentUser) >
								<a class="dropdown-item" href="/${user.id}/${currentUser.id}/fromBlackList">unblock</a> 
							<#else>
								<a class="dropdown-item" href="/${user.id}/${currentUser.id}/inBlackList">block</a> 
							</#if>
							
							<#if chat??>
								<#if !chatMembers?seq_contains(user)>
									<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/invate">invate</a> 
								</#if>
								<#if chatMembers?seq_contains(user) && chatAdmins?seq_contains(currentUser) && !chatAdmins?seq_contains(user)>
									<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/chaseOut">chase out</a> 
								</#if>
								<#if !chatAdmins?seq_contains(user) && chatAdmins?seq_contains(currentUser)>
									<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/giveAdmin">give admin</a> 
								</#if>
							</#if>
							
						<#else>
							<#if chat??>
								<#if chatAdmins?seq_contains(user)>
									<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/removeAdmin">take off admin</a> 
								</#if>
							</#if>
						</#if>
					</div>
				</div>
		</#if>
	</li>
</ul>
