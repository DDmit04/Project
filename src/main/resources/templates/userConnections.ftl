<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showListsOfUsers.ftl" as userLists> 
<@shell.htmlPage> 

<#include "parts/security.ftl">

<#assign isCurrentUserSubList = (currentUsername == user.username)>

<div class="col-8">
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<a class="nav-item nav-link <#if listType == 'friends'>active</#if>" data-toggle="tab" href="#nav-friends" role="tab">
				<#if isCurrentUserSubList>
					My friends
				<#else>
					${user.username}'s friends
				</#if>
			</a>
			<a class="nav-item nav-link <#if listType == 'subscriptions'>active</#if>" data-toggle="tab" href="#nav-subscriptions" role="tab">
				<#if isCurrentUserSubList>
					My subscriptions
				<#else>
					${user.username}'s subscriptions
				</#if>
			</a>
			<a class="nav-item nav-link <#if listType == 'subscribesrs'>active</#if>" data-toggle="tab" href="#nav-subscribers" role="tab">
				<#if isCurrentUserSubList>
					My subscribers
				<#else>
					${user.username}'s subscribers
				</#if>
			</a> 
		</div>
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
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
	</div>
</div>

</@shell.htmlPage>