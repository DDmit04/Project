<ul class="list-group list-group-flush shadow border border-secondary mt-1">
	<li class="list-group-item">
		<div class="border-secondary media mt-2">
			<a href="/${comment.commentAuthor.id}/profile"> 
				<#if comment.commentAuthor.userPicName??>
					<img class="mr-2 rounded-circle border border-secondary" 
						 src="/imgUserPic/${comment.commentAuthor.userPicName}" width="34" height="34">
				<#else>
					<img class="mr-2 rounded-circle border border-secondary" 
						 src="http://localhost:8080/static/images/title1.png" width="34" height="34">
				</#if>
			</a>
			<div class="media-body">
				<a href="/${comment.commentAuthor.id}/profile" class="h6 ml-2">${comment.commentAuthor.username}</a>
				<div class="ml-2">
					${comment.commentText}
					<div class="my-2">
						<#if comment.commentPicName??>
	       					<img src="/imgCommentPic/${comment.commentPicName}" width="120" height="120">
	    				</#if>
							<small>${comment.creationDate}</small>
					</div>
			</div>
		</div>
		<#if currentUsername== comment.commentAuthor.username>
			<div class="col dropdown" align="right">
				<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
					<i class="fas fa-ellipsis-v"></i>
				</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<#if !isEdit> 
						<a class="dropdown-item" href="/${post.id}/comments/${comment.id}/edit">edit</a> 
					</#if>
					<a class="dropdown-item" href="/${post.id}/comments/${comment.id}/delete">delete</a>
				</div>
			</div>
		</#if>
	</li>
</ul>