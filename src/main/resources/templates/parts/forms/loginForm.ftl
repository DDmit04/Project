<!-- uses in login -->

<form class="form-signin" method="post" action="">
      <img class="mb-4" src="http://localhost:8080/static/images/title.png" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
      
	  <input name="username" id="username" type="text" class="form-control" placeholder="Username" 
	      	onfocus="disposeAlert('username');">
	  <div id="usernameError" class="invalid-feedback"></div>
	  
	  <input name="password" id="password" type="password" class="form-control mt-2" placeholder="Password" 
	      	onfocus="disposeAlert('password');">
	  <div id="passwordError" class="invalid-feedback"></div>
	      
      <button class="btn btn-lg btn-primary btn-block mt-3" type="submit" value="Sign in">Sign in</button>
      <h5 class="h5 mt-3 font-weight-normal">or</h5>
      <h5 class="h5 mt-3 font-weight-normal">go to <a href="/registration">registration</a></h5> 
      <h5 class="h5 mt-3 font-weight-normal">or maybe you</h5>
      <h5><a href="/passwordRecover">forgot the password</a></h5>
      <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

<script src="/customJs/formValidate.js"></script>