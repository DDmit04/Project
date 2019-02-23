

<nav class="navbar navbar-expand-lg navbar-primary navbar-default bg-light scrolling-navbar fixed-top shadow">
  <div class="container-fluid">
	  <div class="row">
		 <a class="navbar-brand" href="/">
			 <img src="http://localhost:8080/static/images/title1.png" width="30" height="30" class="d-inline-block align-top">
		 </a>
		 <div class="collapse navbar-collapse" id="navbarContent">
		    <ul class="navbar-nav mr-auto">
		    	<li class="nav-item">
		    		<a class="nav-link waves-effect" href="/">greeting</a>
		    	</li>
		    	<li class="nav-item">
		    		<a class="nav-link waves-effect" href="/posts">Posts</a>
		    	</li>
		    </ul>
		 </div>
		 <form class="form-inline my-2 my-lg-0">
		 	<input class="form-control mr-sm-2" type="text" name="search" placeholder="Search" value="${search?ifExists}">
		 	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		 </form>
		 </div>
		 <#include "/parts/forms/logout.ftl">
	 </div>
</nav>