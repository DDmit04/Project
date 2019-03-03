<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage>

<div class="col-9">
	<div><h3>Requests sent by you:</h3></div>
	<#list friendRequestsFrom as from>
		<ul class="list-group list-group-flush shadow border border-secondary mt-1">
			<li class="list-group-item">
				<div class="border-secondary media">
					<a href="/${from.requestTo.id}/profile"> 
						<#if from.requestFrom.userPicName??>
							<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${from.requestTo.userPicName}" width="34" height="34" class="mr-3">
						<#else>
							<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
						</#if>
					</a>
					<div class="media-body">
						<a href="/${from.requestTo.id}/profile" class="h6 ml-2">${from.requestTo.username}</a>
						<div class="ml-2">
							${from.friendRequestText}
							<div class="my-2">
								<small>${from.creationDate}</small>
							</div>
							<div class="mt-2">
								<a class="btn btn-primary" href="/${from.requestTo.id}/friendRequest/${from.id}/denial"role="button">denial</a>
							</div>
						</div>
					</div>
				</div>
			</li>
		</ul>
	<#else>
		<h2 class="display-4 mt-3" align="center">No request sent by you</h2>
	</#list>
</div>
<hr class="my-5">
<div class="col-9">
	<div><h3>Requests sent to you:</h3></div>
	<#list friendRequestsTo as to>
		<ul class="list-group list-group-flush shadow border border-secondary mt-1">
			<li class="list-group-item">
				<div class="border-secondary media">
					<a href="/${to.requestFrom.id}/profile"> 
						<#if to.requestFrom.userPicName??>
							<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${to.requestFrom.userPicName}" width="34" height="34" class="mr-3">
						<#else>
							<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
						</#if>
					</a>
					<div class="media-body">
						<div><a href="/${to.requestFrom.id}/profile" class="h6 ml-2">${to.requestFrom.username}</a></div>
						<div class="ml-2">
							<div>${to.friendRequestText}</div>
							<div class="my-2">
								<small>${to.creationDate}</small>
								<div class="mt-2">
									<a class="btn btn-primary" href="/${to.requestFrom.id}/friendRequest/${to.id}/denial"role="button">denial</a>
									<a class="btn btn-primary" href="/${to.requestFrom.id}/friendRequest/${to.id}/accept"role="button">accept</a>
								</div>
							</div>
						</div>
					</div>
			</div>
			</li>
		</ul>
	<#else>
		<h2 class="display-4 mt-3" align="center">no reqest sent to you</h2>
	</#list> 
</div>
</@shell.htmlPage> 