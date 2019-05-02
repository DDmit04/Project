<!-- uses in userProfile
			 group
			 chatSettings
			 chatList
			 chat
			 parts/show/showUser 
			 parts/show/showProfile
			 parts/show/showPost
			 parts/show/showGroup
			 parts/show/showFriendRequest
			 parts/show/showFriend 
			 parts/show/showComment 
			 parts/components/profile/noAccesProfile -->

<#macro pic picAuthor picType picTarget>

<#assign  isPicExist = (picAuthor.groupPicName?? || picAuthor.userPicName?? || picAuthor.chatPicName??)>

<#if picTarget?contains("group")>
		<#assign linkToAuthor = "/groups/${picAuthor.id}"
					defaultPicName = "defaultGroupPic.png">
			<#if isPicExist>
				 <#assign picPath = "/imgGroupPic/${picAuthor.groupPicName}">
			</#if>
<#elseif picTarget?contains("user")>
		<#assign linkToAuthor = "/${picAuthor.id}/profile"
					defaultPicName = "defaultUserPic.png">
		<#if isPicExist>
			<#assign picPath = "/imgUserPic/${picAuthor.userPicName}">
		</#if>
<#elseif picTarget?contains("chat")>
		<#assign linkToAuthor = "/chats/${picAuthor.id}"
					defaultPicName = "defaultChatPic.png">
		<#if isPicExist>
			<#assign picPath = "/imgChatPic/${picAuthor.chatPicName}">
		</#if>
</#if>

<#if picType == "bigPic">
		<#assign picWidth = "228" picHeight = "240">
<#elseif picType == "mediumPic">
		<#assign picWidth = "55" picHeight = "55">
<#elseif picType == "smallPic">
		<#assign picWidth = "34" picHeight = "34">
<#elseif picType == "sideBlockPic">
		<#assign picWidth = "55" picHeight = "55">
</#if>

<#if picType == "bigPic">

	<#if isPicExist>
		<img class="mx-1 border border-secondary" src=${picPath} width=${picWidth} height=${picHeight} >
	<#else>
		<img class="mx-1 border border-secondary" src="http://localhost:8080/static/images/${defaultPicName}" width=${picWidth} height=${picHeight} >
	</#if>
<#elseif picType == "mediumPic" || picType == "smallPic" || picType == "sideBlockPic">
	<a href=${linkToAuthor}>
		<#if isPicExist>
			<img class="mx-1 rounded-circle border border-secondary" src=${picPath} width=${picWidth} height=${picHeight}>
		<#else>
			<img class="mx-1 rounded-circle border border-secondary" src="http://localhost:8080/static/images/${defaultPicName}" width=${picWidth} height=${picHeight}>
		</#if>
	</a>
</#if>

</#macro>