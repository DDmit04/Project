<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showSub.ftl" as subs> 
<@shell.htmlPage> 

<#include "parts/security.ftl">

<#assign isCurrentUserSubList = (currentUsername == user.username)>

<div class="col-8">
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<a class="nav-item nav-link <#if subListType == 'subscriptions'>active</#if>" data-toggle="tab" href="#nav-subscriptions" role="tab">
				<#if isCurrentUserSubList>
					My subscriptions
				<#else>
					${user.username}'s subscriptions
				</#if>
			</a>
			<a class="nav-item nav-link <#if subListType == 'subscribesrs'>active</#if>" data-toggle="tab" href="#nav-subscribers" role="tab">
				<#if isCurrentUserSubList>
					My subscribers
				<#else>
					${user.username}'s subscribers
				</#if>
			</a> 
		</div>
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade <#if subListType == 'subscriptions'>show active</#if>" id="nav-subscriptions" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 5px 15px;">
			<@subs.showSub subscriptions "subscriptions" />
		</div>
		<div class="tab-pane fade <#if subListType == 'subscribesrs'>show active</#if>" id="nav-subscribers" role="tabpanel" 
			aria-labelledby="nav-subscribers-tab" style="padding: 5px 15px;">
			<@subs.showSub subscribers "subscribers" />
		</div>
	</div>
</div>

</@shell.htmlPage>