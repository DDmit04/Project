<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<div class="row">
	<div class="col-2"></div>
	<div class="card col-8 shadow">
		<div class="card-body">
			<div class="text-center card-text">
				<h1 class="h3 mb-3 font-weight-normal">Registration</h1>
			</div>
			<form method="post" enctype="multipart/form-data" onsubmit="return validateRegistration();">
				<div class="form-group">
				    <label>Username:</label>
					<input class="form-control mb-2 ${(usernameError??)?string('is-invalid', '')}" type="text" 
						name="username" id="username" placeholder="enter username" 
						value="${registrationName?ifExists}" onfocus="disposeAlertUsername();">
					<div id="usernameError" class="invalid-feedback"><#if usernameError??>${usernameError}</#if></div>
				</div>
				<div class="form-group">
				    <label>Passowd:</label>
					<input name="password" id="password" class="form-control mb-2" type="password" placeholder="enter password" 
						onfocus="disposeAlertPassword();">
					<div id="passwordError" class="invalid-feedback"></div>
				</div>
				<div class="form-group my-3">
					<label>Profile picture (optional)</label>
	  				<div class="custom-file">
	    				<input type="file" name="userPic" class="custom-file-input">
	    				<label class="custom-file-label">Choose file</label>
	  				</div>
				</div>
				<button class="btn btn-primary" type="submit">create user</button>
				<input type="hidden" name="_csrf" value="${_csrf.token}" />
			</form>
		</div>
	</div>
	<div class="col-2"></div>
</div>
<script type="application/javascript">
			$('.custom-file-input').on('change', function() { 
				   let fileName = $(this).val().split('\\').pop(); 
				   $(this).next('.custom-file-label').addClass("selected").html(fileName); 
				});
</script>
<script src="/customJs/formValidate.js"></script>
</@shell.htmlPage> 
