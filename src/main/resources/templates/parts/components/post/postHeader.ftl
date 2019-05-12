<#import "/parts/utils/showPics.ftl" as picture>

<#if post.postAuthor??>
	<#assign postAuthor = post.postAuthor>
<#elseif post.postGroup??>
	<#assign postGroup = post.postGroup>
</#if>

<#if postAuthor??>
	<@picture.pic postAuthor "mediumPic" "userPost" />
<#elseif postGroup??>
	<@picture.pic postGroup "mediumPic" "groupPost" />
</#if>
<div class="media-body">
	<div class="mb-2 ml-2">
		<#if postAuthor??>
			<a href="${postAuthor.id}/profile" class="h5">${postAuthor.username}</a>
		<#elseif postGroup??>
			<a href="/groups/${postGroup.id}" class="h5">${postGroup.groupName}</a>
		</#if>
	</div>
	<small class="ml-2">
		${post.postCreationDate?datetime("yyyy-MM-dd'T'HH:mm:ss")?string("MM-dd-yy HH:mm")}
	</small>
</div>