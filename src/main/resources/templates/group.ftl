<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/utils/showPics.ftl" as picture>
<@shell.htmlPage>

<#include "parts/security.ftl">

<div class="row">
	<div class="col-8 ml-4">
		<div class="card border-secondary shadow mb-3">
			<div class="card-header">
				<h5>${group.groupName}</h5>
				${group.groupTitle!"no title"}
			</div>
			<div class="card-body">
				<div class="mb-2">
					${group.groupInformation!"no title"}
				</div>
				<a href="/groups/${group.id}/socialList/groupSubscrabers" role="button" class="btn btn-primary">
 					subs: <span class="badge badge-light">${group.subCount}</span>
				</a>
				<a href="/groups/${group.id}/socialList/groupAdmins" role="button" class="btn btn-primary">
 					admins: <span class="badge badge-light">${group.adminCount}</span>
				</a>
				<a href="/${group.groupOwner.id}/profile" role="button" class="btn btn-primary">
 					Owner
				</a>
			</div>
		</div>
		<#if !user.isBannedInGroup>
			<#if user.isGroupAdmin>
				<#include "parts/forms/addPostForm.ftl">
			</#if>
			<#include "parts/postListHendle.ftl">
		<#else>
			<h4 class="display-4 ml-2 text-center">you were banned in this group</h4>
		</#if>
	</div>
	<div class="col-3">
		<div class="card border-secondary shadow" style="width: 240px;">
			<div class="card-body">
		  		<@picture.pic group "bigPic" "groupAvatar" />
		  		<div class="container">
			  		<div class="row mt-3">
			  			<#if !user.isGroupSub>
			  				<a class="<#if user.isBannedInGroup>disabled</#if> btn btn-primary btn-lg btn-block " role="button" href="/groups/${group.id}/sub">sub</a>
			  			<#elseif user.isGroupAdmin>
							<a class="btn btn-primary btn-lg btn-block" role="button" href="#">redact</a>
						<#else>
			  				<a class="btn btn-primary btn-lg btn-block " role="button" href="/groups/${group.id}/unsub">unsub</a>
						</#if>
			  		</div>
		  		</div>
			</div>
		</div>
		<div class="card border-secondary shadow mt-3" style="height: 300px; width: 240px;">
			<div class="card-body">
				<a href="/groups/${group.id}/socialList/groupSubscriptions">group subs: ${group.subCount}</a>
				<div class="ml-2 mt-2">
					<#assign i = 0>
					<#list groupSubs as sub>
						<#assign i += 1>
						<a href="/${sub.id}/profile">
							<#if sub.userPicName??>
								<img class="mx-1 mt-4 rounded-circle border border-secondary" src="/imgUserPic/${sub.userPicName}" width="50" height="50" alt="...">
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
</div>

</@shell.htmlPage> 