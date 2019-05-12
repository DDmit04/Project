
<#if post.postAuthor??>
	<#assign postAuthor = post.postAuthor>
<#elseif post.postGroup??>
	<#assign postGroup = post.postGroup>
</#if>

<#if postAuthor??>
	<#if currentUsername == postAuthor.username && postType != "repost"> 
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
<#if postGroup??>
	<#if postGroup.groupAdmins?seq_contains(currentUser) && postType != "repost">
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