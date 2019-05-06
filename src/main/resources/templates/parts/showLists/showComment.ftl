<!-- uses in commentList
			 commentEdit -->

<#import "/parts/utils/showPics.ftl" as picture>

<#assign commentAuthor = comment.commentAuthor>
<#if comment.commentedPost??>
	<#assign commentTarget = comment.commentedPost>
<#elseif comment.commentedImage??>
	<#assign commentTarget = comment.commentedImage>
</#if>

<ul class="list-group list-group-flush shadow border border-secondary mt-1 mb-2">
	<li class="list-group-item">
		<div class="border-secondary media">
			<div class="mt-2">
				<@picture.pic comment.commentAuthor "smallPic" "userComment" />
			</div>
			<div class="media-body">
				<a href="/${comment.commentAuthor.id}/profile" class="h6 ml-2 mb-2 mr-2">${comment.commentAuthor.username}</a>
				<small>${comment.commentCreationDate?datetime("yyyy-MM-dd'T'HH:mm:ss")?string("MM-dd-yy HH:mm")}</small>
				<div class="ml-2">
						${comment.commentText}
					<div class="my-1">
						<#if comment.commentImage??>
		       				<a data-toggle="modal" data-target="#exampleModal1">
								<#assign picPath = "/imgUserPic/${comment.commentAuthor.username}/${comment.commentImage.imgFileName}">
					       		<img src="${picPath}" width="120" height="120">
							</a>
						<!-- Modal image -->
						<div class="modal fade" id="exampleModal1" tabindex="-1" role="dialog" aria-hidden="true">
						  <div class="modal-dialog" role="document">
						    <div class="modal-content">
						    	<div class="align-right mr-2 mt-1">
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							        	<span aria-hidden="true">&times;</span>
							        </button>
						        </div>
						      <div class="modal-body">
						        <img src="${picPath}" class="card-img-top">
						      </div>
						      <div class="card-footer bg-transparent">
						        <a role="button" class="btn btn-primary mr-3" href="/images/${comment.commentImage.id}/comments">
						        	go to image
								</a>
						      </div>
						    </div>
						  </div>
						</div>
	    				</#if>
					</div>
				</div>
		</div>
		<#if currentUser == commentAuthor
		    || (commentTarget.postGroup?? && commentTarget.postGroup.groupAdmins?seq_contains(currentUser))
		    || (commentTarget.postAuthor?? && currentUser == commentTarget.postAuthor)
		    || (commentTarget.imgGroup?? && commentTarget.imgGroup.groupAdmins?seq_contains(currentUser))
		    || (commentTarget.imgOwner?? && currentUser == commentTarget.imgOwner) >
			<div class="col dropdown" align="right">
				<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
					<i class="fas fa-ellipsis-v"></i>
				</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<#if !isEdit && currentUser == commentAuthor> 
						<#if post??>
							<a class="dropdown-item" href="/${post.id}/comments/${comment.id}/edit">edit</a> 
						<#elseif image??>
							<a class="dropdown-item" href="/images/${image.id}/comments/${comment.id}/edit">edit</a> 
						</#if>
					</#if>
					<#if currentUser != commentAuthor >
						<#if commentTarget.postGroup?? || commentTarget.imgGroup??>
							<#if commentTarget.imgGroup??>
								<a class="dropdown-item" href="/groups/${commentTarget.imgGroup.id}/${commentAuthor.id}/ban">ban</a> 
							<#elseif commentTarget.postGroup??>
								<a class="dropdown-item" href="/groups/${commentTarget.postGroup.id}/${commentAuthor.id}/ban">ban</a> 
							</#if>
						<#else>
							<a class="dropdown-item" href="/${commentAuthor.id}/${currentUser.id}/inBlackList">block</a> 
						</#if>
					</#if>
					<#if post??>
						<a class="dropdown-item" href="/${post.id}/comments/${comment.id}/delete">delete</a>
					<#elseif image??>
						<a class="dropdown-item" href="/images/${image.id}/comments/${comment.id}/delete">delete</a>
					</#if>
				</div>
			</div>
		</#if>
	</li>
</ul>