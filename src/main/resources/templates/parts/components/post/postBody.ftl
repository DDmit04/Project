<#import "/parts/showLists/showImage.ftl" as showImage>  

<#if isEdit && (post.repostImage?? || post.postImage??)>
	<div align="right">
		<#if post.postImage??>
			<a href="/${post.id}/removeImage">delete</a>
		<#elseif post.repostImage??>
			<a href="/images/${post.id}/removeRepostImage">delete</a>
		</#if>
	</div>
</#if>
<#if post.tags != "">
	<strong class="ml-1 mb-1"><a href="/posts?search=${post.tags}">#${post.tags}</a></strong>
</#if>
<div class="my-2 ml-1 card-text">${post.postText}</div>
<div class="shadow">
	<#if post.postImage??>
		<@showImage.show post.postImage />
	</#if>
  	</div>
<!--    starts recursion while post (or post in repost)  -->
<#if post.repost??>
	<@show post.repost post "repost" />
</#if>
<#if post.repostImage??>
	<@showImage.show post.repostImage/>		
</#if>