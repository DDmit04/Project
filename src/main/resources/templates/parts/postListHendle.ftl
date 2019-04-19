<!-- uses in postList
			 userProfile
			 group
			 parts/components/profile/noAccesProfile
			 searchList  -->

<#import "showLists/showPost.ftl" as showPost> 

<#if !isEdit>
	<#list posts as post>
		<@showPost.show post 0 "post" />
	<#else>
		<h2 class="display-4 mt-5" align="center">Oops! Nothing here!</h2>
	</#list>
<#else>
	<@showPost.show post 0 "post" />
</#if>