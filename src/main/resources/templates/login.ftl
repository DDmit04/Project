<#import "parts/HTMLshell.ftl" as shell>

<@shell.htmlPage>

<div class="container">
	<div class="row">
		<div class="col"></div>
			<div class="col-8 text-center">
			<div class="card shadow">
			<div class="card-body">
				<form class="form-signin" method="post" action="/login">
				      <img class="mb-4" src="http://localhost:8080/static/images/title1.png" alt="" width="72" height="72">
				      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
				      
					  <input name="username" id="username" type="text" class="form-control" placeholder="Username" 
					      	onfocus="disposeAlertUsername();">
					  <div id="usernameError" class="invalid-feedback"></div>
					  
					  <input name="password" id="password" type="password" class="form-control mt-2" placeholder="Password" 
					      	onfocus="disposeAlertPassword();">
					  <div id="passwordError" class="invalid-feedback"></div>
					      
				      <button class="btn btn-lg btn-primary btn-block mt-3" type="submit" value="Sign in">Sign in</button>
				      <h5 class="h5 mt-3 font-weight-normal">or</h5>
				      <h5 class="h5 mt-3 font-weight-normal">go to <a href="/registration">registration</a></h5> 
				      <input type="hidden" name="_csrf" value="${_csrf.token}" />
				</form>
			</div>
			</div>
			</div>
		<div class="col"></div>
	</div>
</div>
<script src="/customJs/formValidate.js"></script>
<script>
	window.onload = validateLogin;
</script>
</@shell.htmlPage>