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
			<#assign thisUserIsGroupAdmin = groupAdmins?seq_contains(user)
					 thisUserInGroupBanList = groupBanList?seq_contains(user)
					 currentUserIsGroupAdmin = groupAdmins?seq_contains(currentUser)
					 groupOwner = group.groupOwner>
					  
			<#if groupOwner == currentUser && currentUser != user>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					
						<#if !thisUserInGroupBanList>
							<#if thisUserIsGroupAdmin>
								<a class="dropdown-item" href="/groups/${group.id}/${user.id}/removeAdmin">remove admin</a> 
								<a class="dropdown-item" href="/groups/${group.id}/${user.id}/makeOwner">make owner</a>
							<#else>
								<a class="dropdown-item" href="/groups/${group.id}/${user.id}/addAdmin">give admin</a>
							</#if>
						</#if>
						<#if thisUserInGroupBanList>
							<a class="dropdown-item" href="/groups/${group.id}/${user.id}/unban">unban</a>
						<#else>
							<a class="dropdown-item" href="/groups/${group.id}/${user.id}/ban">ban</a>
						</#if>
						
					</div>
				</div>
			</#if>
			<#if currentUser != user && currentUserIsGroupAdmin && groupOwner != user && groupOwner != currentUser>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<#if thisUserInGroupBanList>
							<a class="dropdown-item" href="/groups/${group.id}/${user.id}/unban">unban</a>
						</#if>
						<#if !thisUserInGroupBanList>
							<a class="dropdown-item" href="/groups/${group.id}/${user.id}/ban">ban</a>
						</#if>					
					</div>
				</div>
			</#if>
			<#if groupOwner != currentUser && currentUser == user && currentUserIsGroupAdmin>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<a class="dropdown-item" href="/groups/${group.id}/${currentUser.id}/removeAdmin">take off admin</a> 
					</div>
				</div>
			</#if>
		<#elseif chat??>
			<#assign currentUserIsChatAdmin = chatAdmins?seq_contains(currentUser)
					 thisUserIsChatMember = chatMembers?seq_contains(user)
					 thisUserIsChatAdmin = chatAdmins?seq_contains(user)
					 chatOwner = chat.chatOwner>	
					 
			<#if user.username != currentUsername && chatOwner != user 
				&& (currentUserIsChatAdmin || !thisUserIsChatMember || !thisUserIsChatAdmin)>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					
						<#if !thisUserIsChatMember && !chatBanList?seq_contains(user)>
							<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/invate">invate</a> 
						<#elseif currentUserIsChatAdmin && thisUserIsChatMember && !thisUserIsChatAdmin>
							<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/chaseOut">chase out</a> 
							<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/giveAdmin">give admin</a> 
						</#if>
						<#if (currentUserIsChatAdmin || (chatOwner == currentUser)) && !thisUserIsChatAdmin>
							<#if !chatBanList?seq_contains(user)>
								<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/ban">ban</a> 
							<#else>
								<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/unban">unban</a> 
							</#if>
						</#if>
						<#if chat.chatOwner == currentUser && thisUserIsChatAdmin>
							<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/removeAdmin">remove admin</a> 
							<a class="dropdown-item" href="/chats/${chat.id}/${user.id}/makeOwner">make owner</a> 
						</#if>
						
					</div>
				</div>
			</#if>
			<#if chatOwner != currentUser && currentUser == user && currentUserIsChatAdmin>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<a class="dropdown-item" href="/chats/${chat.id}/${currentUser.id}/removeAdmin">take off admin</a> 
					</div>
				</div>
			</#if>
		<#else>
			<#if user.username != currentUsername>
				<div class="col dropdown" align="right">
					<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<#if user.inBlackList?seq_contains(currentUser) >
							<a class="dropdown-item" href="/${user.id}/${currentUser.id}/fromBlackList">unblock</a> 
						<#else>
							<a class="dropdown-item" href="/${user.id}/${currentUser.id}/inBlackList">block</a> 
						</#if>
					</div>
				</div>
			</#if>
		</#if>
	</li>
</ul>
