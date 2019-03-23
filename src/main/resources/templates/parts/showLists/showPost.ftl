<#macro show post parentPost postType>

<#import "/parts/utils/showPics.ftl" as picture>
<#include "/parts/security.ftl">

<div class="card mt-3 shadow border-secondary">
	<div class="card-header border-secondary media">
		<#if post.postAuthor??>
			<a href="/${post.postAuthor.id}/profile">
				<#if post.postAuthor.userPicName??>
					<img class="mr-3 rounded-circle border border-secondary" src="/imgUserPic/${post.postAuthor.userPicName}" width="55" height="55">
				<#else>
					<img class="mr-3 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="55" height="55">
				</#if>
			</a>
		<#elseif post.postGroup??>
			<a href="/groups/${post.postGroup.id}">
				<#if post.postGroup.groupPicName??>
					<img class="mr-2 rounded-circle border border-secondary" src="/imgGroupPic/${post.postGroup.groupPicName}" width="55" height="55" class="mr-3">
				<#else>
					<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="55" height="55" class="mr-3">
				</#if>
			</a>
		</#if>
		<div class="media-body mt-0">
			<div class="mb-2 ml-2">
				<#if post.postAuthor??>
					<a href="${post.postAuthor.id}/profile" class="h5">${post.postAuthor.username}</a>
				<#elseif post.postGroup??>
					<a href="/groups/${post.postGroup.id}" class="h5">${post.postGroup.groupName}</a>
				</#if>
			</div>
			<small class="ml-2">${post.creationDate}</small>
		</div>
		<#if post.postAuthor??>
			<#if currentUsername == post.postAuthor.username && postType != "repost"> 
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
		</#if>
		<#if post.postGroup??>
			<#if post.postGroup.groupAdmins?seq_contains(currentUser) && postType != "repost">
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
		</#if>
		<#if postType == "repost">
			<div align="right">
				<#if !isEdit>
					<a href="/${post.id}/comments"><i class="fas fa-external-link-square-alt"></i></a>
				<#else>
					<a href="/${parentPost.id}/removeRepost">delete</a>
				</#if>
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
<!--    starts recursion while post (or post in repost) have repost, build post three  -->
    	<#if post.repost??>
			<@show post.repost post "repost" />
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
			<#if !post.postAuthor?? || currentUsername != post.postAuthor.username>
				<a class="ml-3" data-toggle="collapse" href="#repost${post.id}" role="button" aria-expanded="false" aria-controls="repost${post.id}"
					onclick="rotateIcon();">
		    		<i id="repostIcon" class="fas fa-sign-in-alt mr-1 "></i>${post.repostsCount}
		  		</a>
				<div class="collapse mt-3" id="repost${post.id}">
		 		 	<form method="post" action="/${post.id}/repost">
						<input id="repostText" name="repostText" class="form-control col-mt" type="text"  placeholder="repost text"> 
						<input id="repostTags" name="repostTags" class="form-control col-mt mt-2" type="text"  placeholder="repost tags"> 
						<button class="btn btn-primary mt-2" type="submit">repost</button>
						<input type="hidden" name="_csrf" value="${_csrf.token}" />
					</form>	
				</div>
			</#if>
		</div>
	</#if>
</div>
<script>
	function rotateIcon() {
		var icon = document.getElementById("repostIcon");
		if(icon.classList.contains('fa-rotate-90')) {
			setTimeout(function() {
				icon.classList.remove('fa-rotate-90');
			}, 350);
		} else {
			setTimeout(function() {
				icon.classList.add('fa-rotate-90');	
			}, 350);
		}
	}
</script>
<script src="/customJs/formValidate.js"></script>

</#macro>