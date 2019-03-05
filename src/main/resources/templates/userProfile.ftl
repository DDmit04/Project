<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="row">
	<div class="col-3">
		<div class="card border-secondary shadow" style="width: 240px;">
			<div class="card-body">
		  		<img src="http://localhost:8080/static/images/title1.png" width="200" height="200" class="card-img-top" alt="...">
		  		<div class="container">
			  		<div class="row mt-3">
				  		<#if currentUsername != user.username>
				  			<#if user.isFriend>
								<a class="btn btn-primary" href="/${user.id}/deleteFriend" role="button">Unfrend</a>
							<#elseif user.isRequested>
								<a class="btn btn-primary disabled" href="/${user.id}/deleteFriend" role="button">request sent</a>
				  			<#else>
								<a class="btn btn-primary" href="/${user.id}/friendRequest" role="button">Frend reqest</a>
				  			</#if>
				  		</#if>
			  		</div>
		  		</div>
			</div>
		</div>
		<div class="card border-secondary shadow mt-3" style="height: 240px; width: 240px;">
			<div class="card-body">
				user Groups
			</div>
		</div>
	</div>
	<div class="col-8 ml-4">
		<div class="card border-secondary shadow mb-3">
			<div class="card-header">
				<h5>${user.username}</h5>
			</div>
			<div class="card-body">
				<a href="/${user.id}/profile/friendlist" role="button" class="btn btn-primary">
 					friends: <span class="badge badge-light">${user.friendCount}</span>
				</a>
			</div>
		</div>
		<#if currentUsername == user.username>
			<#include "parts/forms/addPostForm.ftl">
		</#if>
		<#if !isEdit>
			<#list posts as post>
				<#include "parts/posts/showPost.ftl">
			<#else>
				<h2 class="display-4 mt-5" align="center">Oops! Nothing here!</h2>
			</#list>
		<#else>
			<#include "parts/posts/showPost.ftl">
	</#if>
	</div>
</div>
</@shell.htmlPage>