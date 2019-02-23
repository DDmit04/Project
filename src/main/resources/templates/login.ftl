<#import "parts/HTMLshell.ftl" as shell>

<@shell.htmlPage>

<div class="container">
	<div class="row">
		<div class="col"></div>
			<div class="col-6 text-center">
				<form class="form-signin" method="post" action="/login">
				      <img class="mb-4" src="http://localhost:8080/static/images/title1.png" alt="" width="72" height="72">
				      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
				      <input type="text" class="form-control mb-2" name="username" placeholder="Username">
				      <input type="password" class="form-control" name="password" placeholder="Password">
				      <input type="hidden" name="_csrf" value="${_csrf.token}" />
				      <button class="btn btn-lg btn-primary btn-block mt-3" type="submit" value="Sign in">Sign in</button>
				      <h5 class="h5 mt-3 font-weight-normal">or</h5>
				      <h5 class="h5 mt-3 font-weight-normal">go to <a href="/registration">registration</a></h5> 
				</form>
			</div>
		<div class="col"></div>
	</div>
</div>
</@shell.htmlPage>