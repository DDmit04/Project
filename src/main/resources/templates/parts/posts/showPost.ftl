<div class="card mt-3 border-dark shadow">
	<div class="card-header border-dark media">
	<img class="mx-2" src="http://localhost:8080/static/images/title.png" width="64" height="64" class="mr-3">
		<div class="media-body mt-0">
			<h5 class="mt-0">username</h5>
			<small class="ml-2">${post.creationDate}</small>
		</div>
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
	</div>
	<div class="card-body">
		<#if post.tags != "">
			<strong class="row ml-1 mb-1"><a href="/posts?search=${post.tags}">#${post.tags}</a></strong>
		</#if>
		<div class="row mb-mt-2 ml-1">${post.postText}</div>
		<#if post.filename??>
       		<img src="/img/${post.filename}" width="89" class="card-img-top">
    	</#if>
	</div>
</div>



<!-- <div class="card mt-3 border-dark shadow"> -->
<!-- 	<div class="card-header border-dark media"> -->
<!-- 	<img class="mx-2" src="images/123.png" width="64" height="64" class="mr-3"> -->
<!-- 		<div class="media-body mt-0"> -->
<!-- 			<h5 class="mt-0">username</h5> -->
<!-- 			<small class="ml-2">${post.creationDate}</small> -->
<!-- 		</div> -->
<!-- 		<div class="col dropdown" align="right"> -->
<!-- 			<button class="btn btn-light round" type="button" id="dropdownMenuButton" data-toggle="dropdown"> -->
<!-- 				<i class="fas fa-ellipsis-v"></i> -->
<!-- 			</button> -->
<!-- 			<div class="dropdown-menu" aria-labelledby="dropdownMenuButton"> -->
<!-- 				<#if !isEdit> -->
<!-- 					<a class="dropdown-item" href="/${post.id}/edit">edit</a>  -->
<!-- 				</#if> -->
<!-- 					<a class="dropdown-item" href="/${post.id}/delete">delete</a> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="card-body"> -->
<!-- 		<strong class="row ml-1">${post.tags}</strong> -->
<!-- 		<div class="row mb-mt-2 ml-1">${post.postText}</div> -->
<!-- 		<#if post.filename??> -->
<!--        		<img src="/img/${post.filename}" width="89" class="card-img-top"> -->
<!--     	</#if> -->
<!-- 	</div> -->
<!-- </div> -->