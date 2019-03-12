<nav class="navbar navbar-expand-lg navbar-primary navbar-default bg-light scrolling-navbar fixed-top shadow">
  <div class="container-fluid">
		 <a class="navbar-brand" href="/posts">
			 <img src="http://localhost:8080/static/images/title1.png" width="30" height="30" class="d-inline-block align-top">
		 </a>
		 <div class="collapse navbar-collapse" id="navbarContent">
		    <ul class="navbar-nav mr-auto">
		    	<li class="nav-item">
		    		<a class="nav-link waves-effect" href="/">greeting</a>
		    	</li>
		    	<li class="nav-item">
		    		<a class="nav-link waves-effect" href="/subscriptionPosts">Subscription posts</a>
		    	</li>
		    	<li class="nav-item">
		    		<#if currentUser??>
			    		<form class="form-inline my-2 my-lg-0" action="/posts?search=${search?ifExists}">
			 				<input class="form-control mr-sm-2" type="text" name="search" placeholder="Search" value="${search?ifExists}">
			 				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			 			</form>
		 			</#if>
		    	</li>
		    </ul>
		 </div>
		 <#if currentUser??>
		 	<div class="mr-3">${currentUsername}</div>
		 	<#include "/parts/forms/logout.ftl">
		 <#else>
		 	<div class="mr-3">Gest</div>
		 	<a class="btn btn-primary" href="/login">sign in</a>
		 </#if>
	 </div>
</nav>