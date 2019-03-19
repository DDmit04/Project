<#import "parts/HTMLshell.ftl" as shell>

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
				${group.groupInformation!"no title"}
				<a href="/groups/${group.id}/socialList/groupSubscriptions" role="button" class="btn btn-primary">
 					subs: <span class="badge badge-light">${group.subCount}</span>
				</a>
				<a href="/groups/${group.id}/socialList/groupAdmins" role="button" class="btn btn-primary">
 					admins: <span class="badge badge-light">${group.adminCount}</span>
				</a>
				<a href="/groups/${group.id}/socialList/groupOwner" role="button" class="btn btn-primary">
 					Owner
				</a>
			</div>
		</div>
		<#include "parts/forms/addPostForm.ftl">
		<#include "parts/postListHendle.ftl">
	</div>
	<div class="col-3">
		<div class="card border-secondary shadow" style="width: 240px;">
			<div class="card-body">
		  		<img src="http://localhost:8080/static/images/title1.png" width="200" height="200" class="card-img-top" alt="...">
		  		<div class="container">
			  		<div class="row mt-3">
			  			<#if !user.isGroupSub>
			  				<a class="btn btn-primary btn-lg btn-block" role="button" href="/groups/${group.id}/sub">sub</a>
			  			<#else>
							<a class="btn btn-primary btn-lg btn-block" role="button" href="/groups/${group.id}/unsub">unsub1</a>
						</#if>
			  		</div>
		  		</div>
			</div>
		</div>
		<div class="card border-secondary shadow mt-3" style="height: 240px; width: 240px;">
			<div class="card-body">
				<a href="/groups/${group.id}/socialList/groupSubscriptions">group subs:</a>
			</div>
		</div>
	</div>
</div>

</@shell.htmlPage> 