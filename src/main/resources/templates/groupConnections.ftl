<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showListsOfUsers.ftl" as userLists> 
<#import "parts/showLists/showGroup.ftl" as groupList> 
<@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<h5 class="display-5 mt-2 mb-3" align="left">
		${group.groupName}'s connections list
	</h5>
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'groupSubscrabers'>active</#if>" data-toggle="tab" 
				href="#nav-groupSubs" role="tab">
				subs (${group.subCount})
			</a>
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'groupAdmins'>active</#if>" data-toggle="tab" 
				href="#nav-groupAdmins" role="tab">
				admins (${group.adminCount})
			</a>
			<#if groupAdmins?seq_contains(currentUser)>
				<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'groupBanList'>active</#if>" data-toggle="tab" 
					href="#nav-groupBanList" role="tab">
					ban List (${group.banCount})
				</a>
			</#if>
		</div>		
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade <#if listType == 'groupSubscrabers'>show active</#if>" id="nav-groupSubs" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers groupSubscrabers "groupSubscrabers" />
		</div>
		<div class="tab-pane fade <#if listType == 'groupAdmins'>show active</#if>" id="nav-groupAdmins" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers groupAdmins "groupAdmins" />
		</div>
		<#if groupAdmins?seq_contains(currentUser)>
			<div class="tab-pane fade <#if listType == 'groupBanList'>show active</#if>" id="nav-groupBanList" role="tabpanel" 
				aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
				<@userLists.showUsers banList "groupBanList" />
			</div>
		</#if>
	</div>
</div>

<!-- Modal take off admin -->
<div class="modal fade" id="takeOfAdmin" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Admin take off</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	Are you sure you want to remove the admin rights in group ${group.groupName}?
      </div>
      <div class="modal-footer">
      	<a class="btn btn-primary" role="button" href="/groups/${group.id}/${currentUser.id}/removeAdmin">yes</a> 
        <button type="button" class="btn btn-secondary" data-dismiss="modal">no</button>
      </div>
    </div>
  </div>
</div>

</@shell.htmlPage>