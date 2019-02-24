<div class="card mt-3 border-dark shadow">
	<div class="card-header border-dark media">
		<#if post.postAthor.userPicName??>
			<img class="mr-2 rounded-circle border border-dark" src="/imgUserPic/${post.postAthor.userPicName}" width="55" height="55" class="mr-3">
		<#else>
			<img class="mr-2 rounded-circle border border-dark" src="http://localhost:8080/static/images/title.png" width="55" height="55" class="mr-3">
		</#if>
		<div class="media-body mt-0">
			<div class="mb-2 ml-2">
				<a href="${post.postAthor.username}/prifile" class="h5">${post.postAthor.username}</a>
			</div>
			<small class="ml-2">${post.creationDate}</small>
		</div>
		<#if username == post.postAthor.username>
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
	<div class="card-body">
		<#if post.tags != "">
			<strong class="row ml-1 mb-1"><a href="/posts?search=${post.tags}">#${post.tags}</a></strong>
		</#if>
		<div class="row my-2 ml-1">${post.postText}</div>
		<div class="shadow">
			<#if post.filename??>
	       		<img src="/img/${post.filename}" width="89" class="card-img-top">
	    	</#if>
    	</div>
	</div>
</div>