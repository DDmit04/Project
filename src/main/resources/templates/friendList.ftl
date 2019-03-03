<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<!-- <ul> -->
<!-- <#list friends as frend> -->
<!-- 	<li class="list-group-item"> -->
<!-- 		<div>${frend.id}</div> -->
<!-- 		<div>${frend.username}</div> -->
<!-- 		<div>${frend.registrationDate}</div> -->
<!-- 	</li> -->
<!-- <#else> -->
<!-- 	no frends -->
<!-- </#list> -->
<!-- </ul> -->

<div class="col-9">
	<div><h3>Your friendlist:</h3></div>
	<#list friends as friend>
		<ul class="list-group list-group-flush shadow border border-secondary mt-1">
			<li class="list-group-item">
				<div class="border-secondary media">
					<a href="/${friend.id}/profile"> 
						<#if friend.userPicName??>
							<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${friend.userPicName}" width="34" height="34" class="mr-3">
						<#else>
							<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
						</#if>
					</a>
					<div class="media-body">
						<a href="/${friend.id}/profile" class="h6 ml-2">${friend.username}</a>
						<div class="ml-2">
							<div class="my-2">
								<small>${friend.registrationDate}</small>
							</div>
						</div>
					</div>
				</div>
			</li>
		</ul>
	<#else>
		<h2 class="display-4 mt-3" align="left">No frends :(</h2>
	</#list>
</div>

</@shell.htmlPage> 
