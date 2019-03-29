<#macro profile profileType>
<#if profileType == "full">
	<#include "/parts/components/profile/accesProfile.ftl">	
<#elseif profileType == "blackListed">
	<#include "/parts/components/profile/noAccesProfile.ftl">	
</#if>

</#macro>