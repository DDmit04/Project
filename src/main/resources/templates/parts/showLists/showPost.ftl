<!-- uses in commentList
			 commentEdit
			 parts/showLists/showListsOfPost -->

<#macro show post parentPost postType>

<#import "/parts/utils/showPics.ftl" as picture>
<#include "/parts/security.ftl">

<div class="card mt-3 shadow border-secondary">
	<div class="card-header border-secondary media">
		<#if post.postAuthor??>
			<@picture.pic post.postAuthor "mediumPic" "userPost" />
		<#elseif post.postGroup??>
			<@picture.pic post.postGroup "mediumPic" "groupPost" />
		</#if>
		<div class="media-body">
			<div class="mb-2 ml-2">
				<#if post.postAuthor??>
					<a href="${post.postAuthor.id}/profile" class="h5">${post.postAuthor.username}</a>
				<#elseif post.postGroup??>
					<a href="/groups/${post.postGroup.id}" class="h5">${post.postGroup.groupName}</a>
				</#if>
			</div>
			<small class="ml-2">
				${post.postCreationDate?datetime("yyyy-MM-dd'T'HH:mm:ss")?string("MM-dd-yy HH:mm")}
			</small>
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
			<strong class="ml-1 mb-1"><a href="/posts?search=${post.tags}">#${post.tags}</a></strong>
		</#if>
		<div class="my-2 ml-1 card-text">${post.postText}</div>
		<div class="shadow">
			<#if post.filename??>
	    	</#if>
			<a data-toggle="modal" data-target="#exampleModal">
	       		<img src="/img/${post.filename}" width="89" class="card-img-top">
			</a>
			
			<!-- Modal image -->
			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			    	<div class="align-right mr-2 mt-1">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				        	<span aria-hidden="true">&times;</span>
				        </button>
			        </div>
			      <div class="modal-body">
			        <img src="/img/${post.filename}" class="card-img-top">
			      </div>
			      <div class="card-footer bg-transparent">
			        <a class="mr-3" href="/posts/${post.id}/${currentUser.id}/like">
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
			      </div>
			    </div>
			  </div>
			</div>
    	</div>
<!--    starts recursion while post (or post in repost)  -->
    	<#if post.repost??>
			<@show post.repost post "repost" />
		</#if>
	</div>
	<#if postType != "repost">
		<div class="card-footer bg-transparent border-secondary">
			<a class="mr-3" href="/posts/${post.id}/${currentUser.id}/like">
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
					onclick="rotateIcon('repostIcon', ${post.id});">
		    		<i id="repostIcon${post.id}" class="fas fa-sign-in-alt mr-1 "></i>${post.repostsCount}
		  		</a>
				<div class="collapse mt-3" id="repost${post.id}">
		 		 	<form method="post" action="/${post.id}/repost">
		 		 		<#if adminedGroups?size != 0>
			 		 		<div class="custom-control custom-checkbox inline">
								<input type="checkbox" class="custom-control-input my-1" name="groupRepostCheckbox" id="groupRepostCheckbox${post.id}"
									onchange="activateGroupSelect(${post.id});">
								<label class="custom-control-label my-1" for="groupRepostCheckbox${post.id}">repost to group</label>
							</div>
							<select class="custom-select my-2" id="groupRepostSelect${post.id}" name="groupRepostSelect" disabled="disabled">
							   <#list adminedGroups as group>
							   		<option value="${group.id}">${group.groupName}</option>
							   </#list>
	  						</select>
  						</#if>
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
	function activateGroupSelect(id) {
		var groupRepostSelect = document.getElementById('groupRepostSelect' + id);
		groupRepostSelect.disabled = true;
		var chbox = document.getElementById('groupRepostCheckbox' + id);
		if (chbox.checked) {
			groupRepostSelect.disabled = false;	
		}
		else {
			groupRepostSelect.disabled = true;
		}
	}
</script>
<script src="/customJs/animations.js"></script>
<script src="/customJs/formValidate.js"></script>

</#macro>