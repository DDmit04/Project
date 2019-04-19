<!-- uses in postList
			 group
			 parts/components/profile/accesProfile
			 searchList  -->
<#import "/parts/showLists/showPost.ftl" as showPost> 

<#list posts as post>
	<@showPost.show post 0 "post" />
<#else>
	<h2 class="display-4 mt-5" align="center">Oops! Nothing here!</h2>
</#list>
