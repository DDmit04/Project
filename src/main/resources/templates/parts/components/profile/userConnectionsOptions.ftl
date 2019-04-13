<!-- parts/components/profile/noAccesProfile  -->

<#if currentUsername != user.username>
							  		
	<#if user.isFriend>
		<a class="btn btn-primary" href="/${user.id}/${currentUserId}/deleteFriend" role="button">Unfriend</a>
	<#elseif user.isRequested>
		<a class="btn btn-primary disabled" href="/${user.id}/deleteFriend" role="button">Requesed</a>
	<#else>
		<a class="btn btn-primary" href="/${user.id}/friendRequest" role="button">Friends</a>
	</#if>
							  			
	<#if user.isSub>
		<a class="btn btn-primary ml-2" href="/${user.id}/unsubscribe">unsub</a>
	<#else>
		<a class="btn btn-primary ml-2" href="/${user.id}/subscribe">sub</a>				  				
	</#if>
	
	<a class="btn btn-primary btn-lg btn-block mt-2" href="/${user.id}/createChat">Send message</a>	
	
<#else>
	<a class="btn btn-primary btn-lg btn-block" href="/profile/redact" role="button">Redact</a>
</#if>