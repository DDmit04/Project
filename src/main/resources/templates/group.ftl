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
				<#if group.groupInformation != "">
					<h5>
				        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne" 
				        		onclick="rotateIcon('groupInformationIcon', 1);" aria-expanded="true" aria-controls="collapseOne">
							group information <i id="groupInformationIcon1" class="fas fa-caret-right ml-2"></i>
				        </button>
				    </h5>
				    <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
				    	<div class="card-body">
				    		${group.groupInformation!"no title"}
				        </div>
				    </div>
				    <hr class="my-2">
			    </#if>
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
		<#if !user.bannedInGroup>
			<#if user.groupAdmin>
				<#include "parts/forms/addPostForm.ftl">
			</#if>
			<#include "/parts/showLists/showListsOfPosts.ftl">
		<#else>
			<h4 class="display-4 ml-2 text-center">you was banned in this group</h4>
		</#if>
	</div>
	<div class="col-3">
		<div class="card border-secondary shadow" style="width: 280px;">
			<div class="card-body">
		  		<@picture.pic group "bigPic" "groupAvatar" />
		  		<div class="container">
			  		<div class="row mt-3">
			  			<#if !user.groupSub>
			  				<a class="<#if user.bannedInGroup>disabled</#if> btn btn-primary btn-lg btn-block " role="button" href="/groups/${group.id}/${currentUser.id}/sub">sub</a>
						<#else>
			  				<a class="btn btn-primary btn-lg btn-block " role="button" href="/groups/${group.id}/${currentUser.id}/unsub">unsub</a>
						</#if>
						<#if user.groupAdmin>
							<a class="btn btn-primary btn-lg btn-block" role="button" href="/groups/${group.id}/redact">redact</a>
						</#if>
			  		</div>
		  		</div>
			</div>
		</div>
		<div class="card border-secondary shadow mt-3" style="height: 300px; width: 280px;">
			<div class="card-body">
				<a href="/groups/${group.id}/socialList/groupSubscriptions">group subs: ${group.subCount}</a>
				<div class="ml-2 mt-2">
					<#assign i = 0>
					<#list groupSubs as sub>
						<#assign i += 1>
						<@picture.pic sub "sideBlockPic" "userAvatar" />
			  			<#if i == 9> 
			  				<#break> 
			  			</#if>
					</#list>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="/customJs/animations.js"></script>
</@shell.htmlPage> 