<#import "/parts/utils/showPics.ftl" as picture>

<ul class="list-group list-group-flush shadow border border-secondary mt-1">
	<li class="list-group-item">
		<div class="border-secondary media">
			<div class="mt-2">
				<@picture.pic comment.commentAuthor "smallPic" "userComment" />
			</div>
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