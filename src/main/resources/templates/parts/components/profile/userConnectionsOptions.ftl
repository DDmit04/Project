<#if currentUsername != user.username>
							  		
	<#if user.isFriend>
		<a class="btn btn-primary" href="/${user.id}/${currentUserId}/deleteFriend" role="button">Unfrend</a>
	<#elseif user.isRequested>
		<a class="btn btn-primary disabled" href="/${user.id}/deleteFriend" role="button">request sent</a>
	<#else>
		<a class="btn btn-primary" href="/${user.id}/friendRequest" role="button">Friend reqest</a>
	</#if>
							  			
	<#if user.isSub>
		<a class="btn btn-primary ml-2" href="/${user.id}/unsubscribe">unsub</a>
	<#else>
		<a class="btn btn-primary ml-2" href="/${user.id}/subscribe">sub</a>				  				
	</#if>
							  	
<#else>
	<a class="btn btn-primary btn-lg btn-block" href="/profile/redact" role="button">Redact</a>
</#if>