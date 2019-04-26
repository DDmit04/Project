<!-- parts/components/profile/noAccesProfile  -->

<#include "/parts/security.ftl">

<#if currentUsername != user.username>
							  		
	<#if user.friend>
		<a class="btn btn-primary" href="/${user.id}/${currentUserId}/deleteFriend" role="button">Unfriend</a>
	<#elseif user.requested>
		<a class="btn btn-primary disabled" href="/${user.id}/deleteFriend" role="button">Requesed</a>
	<#else>
		<a class="btn btn-primary" href="/${user.id}/friendRequest" role="button">Friends</a>
	</#if>
							  			
	<#if user.sub>
		<a class="btn btn-primary ml-2" href="/${user.id}/unsubscribe">unsub</a>
	<#else>
		<a class="btn btn-primary ml-2" href="/${user.id}/subscribe">sub</a>				  				
	</#if>
	
	<a class="btn btn-primary btn-lg btn-block mt-2" href="/${user.id}/createChat">Send message</a>	
	
<#else>
	<a class="btn btn-primary btn-lg btn-block" href="/${currentUser.id}/profile/redact" role="button">Redact</a>
</#if>