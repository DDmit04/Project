<#macro postMacro post postType>

<#include "/parts/security.ftl">

<div class="card mt-3 shadow border-secondary">
	<div class="card-header border-secondary media">
		<a href="/${post.postAuthor.id}/profile">
			<#if post.postAuthor.userPicName??>
				<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${post.postAuthor.userPicName}" width="55" height="55" class="mr-3">
			<#else>
				<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="55" height="55" class="mr-3">
			</#if>
		</a>
		<div class="media-body mt-0">
			<div class="mb-2 ml-2">
				<a href="${post.postAuthor.id}/profile" class="h5">${post.postAuthor.username}</a>
			</div>
			<small class="ml-2">${post.creationDate}</small>
		</div>
		<#if currentUsername == post.postAuthor.username>
			<div class="col dropdown" align="right">
				<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
					<i class="fas fa-ellipsis-v"></i>
				</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<#if !isEdit>
						<a class="dropdown-item" href="/${post.id}/edit">edit</a> 
					</#if>
						<a class="dropdown-item" href="/${post.id}/delete">delete</a>
				</div>
			</div>
		</#if>
		
		
		
		
		
	</div>
	<div class="card-body border-secondary">
		<#if post.tags != "">
			<strong class="row ml-1 mb-1"><a href="/posts?search=${post.tags}">#${post.tags}</a></strong>
		</#if>
		<div class="row my-2 ml-1">${post.postText}</div>
		<div class="shadow">
			<#if post.filename??>
	       		<img src="/img/${post.filename}" width="89" class="card-img-top">
	    	</#if>
    	</div>
    	<#if post.repost??>
			<@postMacro post.repost "repost" />
		</#if>
	</div>
	<#if postType != "repost">
		<div class="card-footer bg-transparent border-secondary">
			<a class="mr-3" href="/posts/${post.id}/like">
				<#if post.liked>
					<i class="fas fa-heart"></i>
				<#else>
					<i class="far fa-heart"></i>
				</#if>
				${post.likes}
			</a>
			<a href="/${post.id}/comments">
				<i class="far fa-comment mr-1"></i>${post.commentsCount}
			</a>
			<#if currentUsername != post.postAuthor.username>
				<a class="ml-3" data-toggle="collapse" href="#repost${post.id}" role="button" aria-expanded="false" aria-controls="repost${post.id}">
		    		<i class="fas fa-sign-in-alt mr-1"></i>1
		  		</a>
				<div class="collapse" id="repost${post.id}">
		 		 	<div class="card card-body">
		 		 		<form method="post" action="${post.id}/repost" onsubmit="return validateComment()">
							<input id="repostText" name="repostText" class="form-control col-mt" type="text"  placeholder="repost text" 
								onfocus="disposeAlert('repostText')"> 
							<input id="repostTags" name="repostTags" class="form-control col-mt" type="text"  placeholder="repost tags" 
								onfocus="disposeAlert('repostTags')"> 
							<div id="commentTextError" class="invalid-feedback"></div>
							<button class="btn btn-primary mt-2" type="submit">
									repost
		<!-- 							<#if editedComment??> -->
		<!-- 								edit -->
		<!-- 							<#else> -->
		<!-- 								add -->
		<!-- 							</#if> -->
							</button>
							<input type="hidden" name="_csrf" value="${_csrf.token}" />
						</form>	
		 			</div>
				</div>
			</#if>
		</div>
	</#if>
</div>
<script src="/customJs/formValidate.js"></script>

</#macro>