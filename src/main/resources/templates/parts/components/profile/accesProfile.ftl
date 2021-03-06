<!-- uses in userProfile -->

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
				<div class="row">
					<div class="ml-2 mt-2">
						<div class="h5">
							${user.username}
						</div>
						<div class="mt-2">
							<span>${user.userStatus?ifExists}</span>
						</div>
					</div>
					<#if currentUsername != user.username>
						<div class="col dropdown" align="right">
							<#include "/parts/components/profile/userBanOptions.ftl">
						</div>
					</#if>
				</div>
			</div>
			<div class="card-body">
				<#if user.userInformation?? && user.userInformation != "">
					<h5>
				        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne" 
				        		onclick="rotateIcon('userInformationIcon', 1);" aria-expanded="true" aria-controls="collapseOne">
							user information <i id="userInformationIcon1" class="fas fa-caret-right ml-2"></i>
				        </button>
				    </h5>
				    <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
				    	<div class="card-body">
				    		${user.userInformation?ifExists}
				        </div>
				    </div>
				    <hr>
				</#if>
				<#include "/parts/components/profile/userConnectionsBlock.ftl">
			</div>
		</div>
		<#if currentUsername == user.username>
			<#include "/parts/forms/addPostForm.ftl">
		</#if>
		<#include "/parts/showLists/showListsOfPosts.ftl">
	</div> 
</div>
<script src="/customJs/animations.js"></script>