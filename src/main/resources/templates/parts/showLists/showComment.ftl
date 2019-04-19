<!-- uses in commentList
			 commentEdit -->

<#import "/parts/utils/showPics.ftl" as picture>

<#assign commentAuthor = comment.commentAuthor
		 commentedPost = comment.commentedPost>

<ul class="list-group list-group-flush shadow border border-secondary mt-1 mb-2">
	<li class="list-group-item">
		<div class="border-secondary media">
			<div class="mt-2">
				<@picture.pic comment.commentAuthor "smallPic" "userComment" />
			</div>
			<div class="media-body">
				<a href="/${comment.commentAuthor.id}/profile" class="h6 ml-2 mb-2">${comment.commentAuthor.username}</a>
				<div class="ml-2">
						${comment.commentText}
					<div class="my-1">
						<#if comment.commentPicName??>
	       					<img src="/imgCommentPic/${comment.commentPicName}" width="120" height="120">
	    				</#if>
							<small>${comment.commentCreationDate}</small>
					</div>
				</div>
		</div>
		<#if currentUser == commentAuthor
		    || (commentedPost.postGroup?? && commentedPost.postGroup.groupAdmins?seq_contains(currentUser))
		    || (commentedPost.postAuthor?? && currentUser == commentedPost.postAuthor) >
			<div class="col dropdown" align="right">
				<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
					<i class="fas fa-ellipsis-v"></i>
				</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<#if !isEdit && currentUser == commentAuthor> 
						<a class="dropdown-item" href="/${post.id}/comments/${comment.id}/edit">edit</a> 
					</#if>
					<#if currentUser != commentAuthor >
						<#if commentedPost.postGroup??>
							<a class="dropdown-item" href="/groups/${commentedPost.postGroup.id}/${commentAuthor.id}/ban">ban</a> 
						<#else>
							<a class="dropdown-item" href="/${commentAuthor.id}/${currentUser.id}/inBlackList">block</a> 
						</#if>
					</#if>
					<a class="dropdown-item" href="/${post.id}/comments/${comment.id}/delete">delete</a>
				</div>
			</div>
		</#if>
	</li>
</ul>