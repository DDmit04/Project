<!-- uses in friendRequestList -->

<#macro showFriendRequest requests requestsType>

<#import "/parts/utils/showPics.ftl" as picture>

<#list requests as request>
<!-- requestFrom - sender - requestType = "from" -->
<!-- requestTo - receiving - requestType = "to" -->
	<#if requestsType == "to">
<!-- 	if receiving, need information about sender that why "requesting = request.requestFrom" -->
		<#assign requesting = request.requestFrom >
	<#else>
		<#assign requesting = request.requestTo >
	</#if>
	<ul class="list-group list-group-flush mt-1" style="border-left: none;
 											 			border-right: none;
 														border-top: none">
			<li class="list-group-item">
				<div class="border-secondary media">
					<div class="mt-2">
						<@picture.pic requesting "mediumPic" "userFriendRequest" />
					</div>
					<div class="media-body">
						<a href="/${requesting.id}/profile" class="h6 ml-2">${requesting.username}</a>
						<div class="ml-2">
							<div class="my-2">
								<small>${request.friendRequestCreationDate}</small>
							</div>
							<div class="mt-2">
								<#if requestsType == "to">
									<a class="btn btn-primary" href="/${requesting.id}/friendRequest/${request.id}/accept"role="button">accept</a>
								</#if>
							<a class="btn btn-primary" href="/${requesting.id}/friendRequest/${request.id}/denial"role="button">denial</a>
							</div>
						</div>
					</div>
				</div>
			</li>
		</ul>
<#else>
	<#if requestsType == "from">
		<h2 class="display-4 ml-2" align="left">No request sent by you</h2>
	<#else>
		<h2 class="display-4 ml-2" align="left">No reqest sent to you</h2>
	</#if>
</#list>

</#macro>