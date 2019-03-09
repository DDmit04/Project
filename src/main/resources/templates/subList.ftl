<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showSub.ftl" as subs> 
<@shell.htmlPage> 

<#include "parts/security.ftl">

<#assign isCurrentUserSubList = (currentUsername == user.username)>

<div class="col-8">
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<#if isCurrentUserSubList> 
				<a class="nav-item nav-link <#if isCurrentUserSubList>active</#if>" data-toggle="tab" href="#nav-subscriptions" role="tab">My subscriptions</a>
			 	<a class="nav-item nav-link" data-toggle="tab" href="#nav-subscribers" role="tab">My subscribers</a> 
			<#else> 
			 	<a class="nav-item nav-link <#if !isCurrentUserSubList>active</#if>" data-toggle="tab" href="#nav-subscribers" role="tab">${user.username}'s subscribers</a>
			</#if>
		</div>
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade <#if isCurrentUserSubList>show active</#if>" id="nav-subscriptions" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 5px 15px;">
			<@subs.showSub subscriptions "subscriptions" />
		</div>
		<div class="tab-pane fade <#if !isCurrentUserSubList>show active</#if>" id="nav-subscribers" role="tabpanel" 
			aria-labelledby="nav-subscribers-tab" style="padding: 5px 15px;">
			<@subs.showSub subscribers "subscribers" />
		</div>
	</div>
</div>

</@shell.htmlPage>