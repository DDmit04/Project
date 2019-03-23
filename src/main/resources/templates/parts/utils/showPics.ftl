<#macro pic basePath mainPath picOwner picType picWidth=55 picHeight=55>


<!-- <img src="/  imgUserPic  /  ${post.postAuthor.  userPicName}" width="55"          height="55" > -->
<!-- <img src="/  basePath    /  ${picOwner.           mainPath}"  width="${picWidth}" height="${picHeight}" > -->


<#if picType == "group">
	<#assign link = "/groups/${picOwner.id}" >
<#elseif picType == "user">
	<#assign link = "/${picOwner.id}/profile" >
</#if>

<a href=link>
	<#if picOwner.mainPath??>
		<img class="mr-2 rounded-circle border border-secondary" src="/basePath/${picOwner.mainPath}" width="${picWidth}" height="${picHeight}" class="mr-3">
	<#else>
		<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="${picWidth}" height="${picHeight}" class="mr-3">
	</#if>
</a>

</#macro>