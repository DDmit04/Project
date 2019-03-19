<ul class="list-group">
	<li class="list-group-item d-flex justify-content-between align-items-center" style="border-left: none;
 																								 border-right: none;
 																								 border-top: none">
		   	<div class="border-secondary media">
				<a href="/${user.id}/profile"> 
					<#if user.userPicName??>
						<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${user.userPicName}" width="34" height="34" class="mr-3">
					<#else>
						<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
					</#if>
				</a>
			<div class="media-body">
				<a href="/${user.id}/profile" class="h6 ml-2">${user.username}</a>
			</div>
		</div>
	</li>
</ul>