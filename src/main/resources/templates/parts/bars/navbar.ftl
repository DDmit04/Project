<!-- uses in parts/HTMLShell -->

<nav class="navbar navbar-expand-lg navbar-primary navbar-default bg-light scrolling-navbar fixed-top shadow">
  <div class="container-fluid">
		 <a class="navbar-brand" href="/posts">
			 <img src="http://localhost:8080/static/images/title.png" width="30" height="30" class="d-inline-block align-top">
		 </a>
		 <div class="collapse navbar-collapse" id="navbarContent">
			 <#if currentUser??>
			    <ul class="navbar-nav mr-auto">
			    	<li class="nav-item">
			    		<a class="nav-link waves-effect" href="/">greeting</a>
			    	</li>
			    	<li class="nav-item">
			    		<a class="nav-link waves-effect" href="/subscriptionPosts">Subscription posts</a>
			    	</li>
			    	<li class="nav-item">
			    		<#if currentUser??>
				    		<form class="form-inline my-2 my-lg-0" action="/search">
				 				<input class="form-control mr-sm-2" type="text" name="search" placeholder="Search" value="${search?ifExists}">
				 				<select class="custom-select mr-2" id="inputGroupSelect" name="searchType">
				 				    <option value="posts" id="posts">posts</option>
    						 		<option value="users" id="users">users</option>
    						 		<option value="groups" id="groups">groups</option>
  							 	</select>
				 				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
				 			</form>
			 			</#if>
			    	</li>
			    </ul>
			 </#if>
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
<script>
	var searchType = '${searchType?ifExists}';
	var posts = document.getElementById("posts");
	var users = document.getElementById("users");
	var groups = document.getElementById("groups");
	if(searchType == "posts") {
		posts.selected = true;
	}  
	else if(searchType == "users") {
		users.selected = true;
	}
	else if(searchType == "groups") {
		groups.selected = true;
	} else {
		posts.selected = true;
	}
</script>