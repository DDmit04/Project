<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<nav>
  <div class="nav nav-pills mr-2" role="tablist">
    <a class="nav-item nav-link" data-toggle="tab" href="#nav-subscriptions" role="tab" aria-controls="nav-home" aria-selected="true">My subscriptions</a>
    <a class="nav-item nav-link" data-toggle="tab" href="#nav-subscribers" role="tab" aria-controls="nav-profile" aria-selected="false">My subscribers</a>
  </div>
</nav>
<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
  <div class="tab-pane fade" id="nav-subscriptions" role="tabpanel" aria-labelledby="nav-subscriptions-tab">
  	<#list subscriptions as sub_ption>
		<ul class="list-group shadow">
  			<li class="list-group-item d-flex justify-content-between align-items-center">
   				<div class="border-secondary media">
					<a href="/${sub_ption.id}/profile"> 
						<#if sub_ption.userPicName??>
							<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${sub_ption.userPicName}" width="34" height="34" class="mr-3">
						<#else>
							<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
						</#if>
					</a>
				<div class="media-body">
					<a href="/${sub_ption.id}/profile" class="h6 ml-2">${sub_ption.username}</a>
				</div>
				</div>
  			</li>
		</ul>
	<#else>
    	no sub1
	</#list>
  </div>
  <div class="tab-pane fade" id="nav-subscribers" role="tabpanel" aria-labelledby="nav-subscribers-tab">
  	<#list subscribers as sub_ber>
		<ul class="list-group shadow">
  			<li class="list-group-item d-flex justify-content-between align-items-center">
   				<div class="border-secondary media">
					<a href="/${sub_ber.id}/profile"> 
						<#if sub_ber.userPicName??>
							<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${sub_ber.userPicName}" width="34" height="34" class="mr-3">
						<#else>
							<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
						</#if>
					</a>
				<div class="media-body">
					<a href="/${sub_ber.id}/profile" class="h6 ml-2">${sub_ber.username}</a>
				</div>
				</div>
  			</li>
		</ul>
	<#else>
    	no sub1
	</#list>
  </div>
</div>

</@shell.htmlPage>