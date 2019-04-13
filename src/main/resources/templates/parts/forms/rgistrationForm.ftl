<!-- uses in registration -->

<form method="post" enctype="multipart/form-data" onsubmit="return validateRegistration();">
	<div class="form-group">
	    <label>Username:</label>
		<input class="form-control mb-2 ${(usernameError??)?string('is-invalid', '')}" type="text" 
			name="username" id="username" placeholder="enter username" 
			value="${registrationName?ifExists}" onfocus="disposeAlert('username');">
		<div id="usernameError" class="invalid-feedback"><#if usernameError??>${usernameError}</#if></div>
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

<script src="/customJs/checkFilename.js"></script>
<script src="/customJs/formValidate.js"></script>