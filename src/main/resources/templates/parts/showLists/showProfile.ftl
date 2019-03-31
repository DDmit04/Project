<#macro profile profileType>

<#include "/parts/security.ftl">

<#if profileType == "full">


	<div class="row">
		<div class="col-3">
			<div class="card border-secondary shadow" style="width: 280px;">
				<div class="card-body">
			  		<@picture.pic user "bigPic" "userProfile" />
			  		<div class="container">
				  		<div class="row mt-3">
					  		<#if currentUsername != user.username>

					  			<#if user.isFriend>
									<a class="btn btn-primary" href="/${user.id}/${currentUserId}/deleteFriend" role="button">Unfrend</a>
								<#elseif user.isRequested>
									<a class="btn btn-primary disabled" href="/${user.id}/deleteFriend" role="button">request sent</a>
					  			<#else>
									<a class="btn btn-primary" href="/${user.id}/friendRequest" role="button">Friend reqest</a>
					  			</#if>

					  			<#if user.isSub>
					  				<a class="btn btn-primary ml-2" href="/${user.id}/unsubscribe">unsub</a>
					  			<#else>
					  				<a class="btn btn-primary ml-2" href="/${user.id}/subscribe">sub</a>				  				
					  			</#if>

					  		<#else>
					  			<a class="btn btn-primary btn-lg btn-block" href="/profile/redact" role="button">Redact</a>
					  		</#if>
				  		</div>
			  		</div>
				</div>
			</div>
			<div class="card border-secondary shadow mt-3" style="height: 300px; width: 240px;">
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
		</div>
		<div class="col-8 ml-4">
			<div class="card border-secondary shadow mb-3">
				<div class="card-header">
					<div class="row h5">
						<div class="ml-2 mt-2">
							${user.username}
						</div>
						<div class="col dropdown" align="right">
							<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
								<i class="fas fa-ellipsis-v"></i>
							</button>
							<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
								<#if user.isBloking>
									<a class="dropdown-item" href="/${user.id}/${currentUser.id}/fromBlackList">unblock</a>
								<#else>
									<a class="dropdown-item" href="/${user.id}/${currentUser.id}/inBlackList">block</a>
								</#if>
							</div>
						</div>
					</div>
				</div>
				<div class="card-body">
					<a href="/${user.id}/profile/socialList/friends" role="button" class="btn btn-primary">
	 					friends: <span class="badge badge-light">${user.friendCount}</span>
					</a>
					<a href="/${user.id}/profile/socialList/subscribesrs" role="button" class="btn btn-primary">
	 					subscribesrs: <span class="badge badge-light">${user.subscribersCount}</span>
					</a>
					<a href="/${user.id}/profile/socialList/subscriptions" role="button" class="btn btn-primary">
	 					subscriptions: <span class="badge badge-light">${user.subscriptionsCount}</span>
					</a>
					<a href="/${user.id}/profile/socialList/groups" role="button" class="btn btn-primary">
	 					groups: <span class="badge badge-light">${user.groupSubscriptionsCount}</span>
					</a>
				</div>
			</div>
			<#if currentUsername == user.username>
				<#include "/parts/forms/addPostForm.ftl">
			</#if>
			<#include "/parts/postListHendle.ftl">
		</div>
	</div>


<#elseif profileType == "blackListed">


	<div class="row">
		<div class="col-3">
			<div class="card border-secondary shadow" style="width: 280px;">
				<div class="card-body">
			  		<@picture.pic user "bigPic" "userProfile" />
				</div>
			</div>
		</div>
		<div class="col-8 ml-4">
			<div class="card border-secondary shadow mb-3">
				<div class="card-header">
					<div class="row h5">
						<div class="ml-2 mt-2">
							${user.username}
						</div>
						<div class="col dropdown" align="right">
							<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
								<i class="fas fa-ellipsis-v"></i>
							</button>
							<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
								<#if user.isBloking>
									<a class="dropdown-item" href="/${user.id}/${currentUser.id}/fromBlackList">unblock</a>
								<#else>
									<a class="dropdown-item" href="/${user.id}/${currentUser.id}/inBlackList">block</a>
								</#if>
							</div>
						</div>
					</div>
				</div>
				<div class="card-body">

				</div>
			</div>
			<h4 class="display-4 ml-2 " align="left">this user blocked you</h4>
		</div>
	</div>
</#if>

</#macro> 