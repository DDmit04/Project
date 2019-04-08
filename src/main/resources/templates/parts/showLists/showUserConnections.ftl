<#import "/parts/showLists/showListsOfUsers.ftl" as userLists> 
<#import "/parts/showLists/showGroup.ftl" as groupList> 

<#include "/parts/security.ftl">

<#assign isCurrentUserList = (currentUsername == user.username)>

	<h5 class="display-5 mt-2 mb-3" align="left">
		<#if isCurrentUserList>
			My connections list
		<#else>
			${user.username}'s connections list
		</#if>
	</h5>
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<#if groups??>
				<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'groups'>active</#if>" data-toggle="tab" 
					href="#nav-groups" role="tab">
						groups (${user.groupSubscriptionsCount})
				</a>
			</#if>
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'friends'>active</#if>" data-toggle="tab" 
				href="#nav-friends" role="tab">
					friends (${user.friendCount})
			</a>
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'subscriptions'>active</#if>" data-toggle="tab" 
				href="#nav-subscriptions" role="tab">
					subscriptions (${user.subscriptionsCount})
			</a>
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'subscribesrs'>active</#if>" data-toggle="tab" 
				href="#nav-subscribers" role="tab">
					subscribers (${user.subscribersCount})
			</a> 
			<#if isCurrentUserList && userBlackList??>
				<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'blackList'>active</#if>" data-toggle="tab" 
					href="#nav-blackList" role="tab">
						black list (${user.blackListCount})
				</a> 
			</#if>
		</div>
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<#if groups??>
			<div class="tab-pane fade <#if listType == 'groups'>show active</#if>" id="nav-groups" role="tabpanel" 
				aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
				<@groupList.groupList groups />
			</div>
		</#if>
		<div class="tab-pane fade <#if listType == 'friends'>show active</#if>" id="nav-friends" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers friends "friends" />
		</div>
		<div class="tab-pane fade <#if listType == 'subscriptions'>show active</#if>" id="nav-subscriptions" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers subscriptions "subscriptions" />
		</div>
		<div class="tab-pane fade <#if listType == 'subscribesrs'>show active</#if>" id="nav-subscribers" role="tabpanel" 
			aria-labelledby="nav-subscribers-tab" style="padding: 15px 5px;">
			<@userLists.showUsers subscribers "subscribers" />
		</div>
		<#if isCurrentUserList && userBlackList??>
			<div class="tab-pane fade <#if listType == 'blackList'>show active</#if>" id="nav-blackList" role="tabpanel" 
				aria-labelledby="nav-subscribers-tab" style="padding: 15px 5px;">
				<@userLists.showUsers userBlackList "userBlackList" />
			</div>
		</#if>
	</div>