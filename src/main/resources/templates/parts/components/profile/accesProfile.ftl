<#include "/parts/security.ftl">

<div class="row ml-2">
	<div class="col-3">
		<div class="card border-secondary shadow" style="width: 280px;">
			<div class="card-body">
				<@picture.pic user "bigPic" "userProfile" />
				<div class="container">
					<div class="row mt-3">
						<#include "/parts/components/profile/userConnectionsOptions.ftl">
			 		</div>
		  		</div>
			</div>
		</div>
		<#include "/parts/components/profile/userGroupsBlock.ftl">
	</div>
	<div class="col-8 ml-4">
		<div class="card border-secondary shadow mb-3">
			<div class="card-header">
				<div class="row h5">
					<div class="ml-2 mt-2">
						${user.username}
					</div>
					<#if currentUsername != user.username>
						<div class="col dropdown" align="right">
							<#include "/parts/components/profile/userBanOptions.ftl">
						</div>
					</#if>
				</div>
			</div>
			<div class="card-body">
				<#include "/parts/components/profile/userConnectionsBlock.ftl">
			</div>
		</div>
		<#if currentUsername == user.username>
			<#include "/parts/forms/addPostForm.ftl">
		</#if>
		<#include "/parts/postListHendle.ftl">
</div> 