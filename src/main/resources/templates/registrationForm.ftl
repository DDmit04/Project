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
				    <label for="exampleInputEmail1">Username:</label>
					<input class="form-control mb-2 ${(usernameError??)?string('is-invalid', '')}" type="text" name="username" id="username" placeholder="enter username" 
						onfocus="disposeAlertUsername();" value="${registrationName?ifExists}">
					<#if usernameError??>
						<div class="invalid-feedback">${usernameError}</div>
					</#if>
				</div>
				<div class="form-group">
				    <label for="exampleInputEmail1">Passowd:</label>
					<input class="form-control mb-2" type="password" name="password" id="password" placeholder="enter password" 
						onfocus="disposeAlertPassword();">
				</div>
				<div class="input-group mt-3">
	  				<div class="custom-file">
	    				<input type="file" name="userPic" class="custom-file-input" id="inputGroupFile03" aria-describedby="inputGroupFileAddon03" >
	    				<label class="custom-file-label" for="inputGroupFile03">Choose file</label>
	  				</div>
				</div>
				<button class="btn btn-primary" type="submit">create user</button>
				<input type="hidden" name="_csrf" value="${_csrf.token}" />
			</form>
		</div>
	</div>
	<div class="col-2"></div>
</div>
<script src="/customJs/formValidate.js"></script>
</@shell.htmlPage> 
