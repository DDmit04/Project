<!-- uses in groupList
			 groupConnections
			 parts/show/showUserConnections
			 searchList  -->
			 
<#macro groupList groups>

<#import "/parts/showLists/showPost.ftl" as showPost> 
	
	<#list groups as group>
		<#include "/parts/showLists/showGroup.ftl">
	<#else>
		<h4 class="display-4 ml-2 " align="<#if !search??>left<#else>center</#if>">No groups :(</h4>
	</#list>

</#macro>