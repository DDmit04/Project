<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showGroup.ftl" as groupList> 
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="row">
	<div class="col-8">
		<nav>
			<div class="nav nav-pills mr-2" role="tablist">
				<a class="nav-item nav-link mr-1 btn-outline-primary active" data-toggle="tab" href="#nav-groupSubs" role="tab">
					my groups(${userGroupsCount})
				</a>
				<a class="nav-item nav-link mr-1 btn-outline-primary" data-toggle="tab" href="#nav-groupAdmins" role="tab">
					all groups
				</a>
				<a href="/${currentUserId}/groups/create" class="btn btn-outline-info" role="button">create group</a>
			</div>		
		</nav>
		<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
			<div class="tab-pane fade show active" id="nav-groupSubs" role="tabpanel" 
				aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
				<@groupList.groupList userGroups />		
			</div>
			<div class="tab-pane fade" id="nav-groupAdmins" role="tabpanel" 
				aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
				<@groupList.groupList allGroups />
			</div>
		</div>
	</div>
	<div class="col-4">
		
	</div>
</div>

</@shell.htmlPage>