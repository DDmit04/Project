<!-- uses in parts/components/profile/noAccesProfile -->

<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
	<i class="fas fa-ellipsis-v"></i>
</button>
<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	<#if user.bloking>
		<a class="dropdown-item" href="/${user.id}/${currentUser.id}/fromBlackList">unblock</a>
	<#else>
		<a class="dropdown-item" href="/${user.id}/${currentUser.id}/inBlackList">block</a>
	</#if>
</div>