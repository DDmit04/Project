<!-- uses in registration -->
<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage> 

<#if registrationAttention??>
	<div class="alert alert-warning col-8" role="alert">
		${registrationAttention}
	</div>
</#if>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="card col-md-5 shadow">
			<div class="card-body">
				<div class="text-center card-text">
					<h1 class="h3 mb-3 font-weight-normal">Registration</h1>
				</div>
				<form method="post" enctype="multipart/form-data" onsubmit="return validateRegistration();">
					<div class="form-group">
					    <label>Username:</label>
						<input class="form-control mb-2 ${(usernameError??)?string('is-invalid', '')}" type="text" 
							name="username" id="username" placeholder="enter username" 
							value="${registrationName?ifExists}" onfocus="disposeAlert('username');">
						<div id="usernameError" class="invalid-feedback"><#if usernameError??>${usernameError}</#if></div>
					</div>
					<div class="form-group">
					    <label>email:</label>
						<input class="form-control mb-2 ${(userEmailError??)?string('is-invalid', '')}" type="text" 
							value="${registrationEmail?ifExists}" name="userEmail" id="userEmail" placeholder="enter email" 
						 onfocus="disposeAlert('userEmail');">
						<div id="userEmailError" class="invalid-feedback"><#if userEmailError??>${userEmailError}</#if></div>
					</div>
					<div class="form-group">
					    <label>Passowd:</label>
						<input name="password" id="password" class="form-control mb-2" type="password" placeholder="enter password" 
							onfocus="disposeAlert('password');">
						<div id="passwordError" class="invalid-feedback"></div>
					</div>
					<div class="form-group">
					    <label>Retype passowd:</label>
						<input name="retypePassword" id="retypePassword" class="form-control mb-2" type="password" placeholder="enter retype password" 
							onfocus="disposeAlert('retypePassword');">
						<div id="retypePasswordError" class="invalid-feedback"></div>
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
		<div class="col-md-6"></div>
	</div>
</div>

<script src="/customJs/checkFilename.js"></script>
<script src="/customJs/formValidate.js"></script>

</@shell.htmlPage> 