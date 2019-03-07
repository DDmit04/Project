<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage>

<div class="col-8">
		<#if redirectMessage != "">
			<div class="alert alert-success alert-dismissible fade show" role="alert">
			  ${redirectMessage}
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		</#if>
		<div class="card shadow">
			<div class="card-body">
				<div class="text-center card-text">
					<h1 class="h3 mb-3 font-weight-normal">Profile settings</h1>
				</div>
        			<button class="btn btn-link" data-toggle="collapse" data-target="#changePassword" aria-expanded="true" aria-controls="changePassword">
        				change password
       	 			</button>
       	 			<!-- Collapse menue -->
       	 			<div id="changePassword" class="collapse <#if currentPasswordError??>show</#if>" aria-labelledby="headingOne" data-parent="#accordion">
		    			<form method="post" action="/profile/settings" onsubmit="return validatePasswords();">
							<div class="form-group mt-3">
							    <label>Current Password:</label>
								<input class="form-control mb-2 ${(currentPasswordError??)?string('is-invalid', '')}" type="text" 
									name="currentPassword" id="currentPassword" placeholder="enter current password" 
									onfocus="disposeAlert('currentPassword');">
								<div id="currentPasswordError" class="invalid-feedback"><#if currentPasswordError??>${currentPasswordError}</#if></div>
							</div>
							<div class="form-group">
							    <label>Passowd:</label>
								<input name="password" id="password" class="form-control mb-2" type="password" placeholder="enter new password" 
									onfocus="disposeAlert('password');">
								<div id="passwordError" class="invalid-feedback"></div>
							</div>
							<div class="form-group">
							    <label>Retype passowd:</label>
								<input name="retypePassword" id="retypePassword" class="form-control mb-2" type="password" placeholder="enter retype new password" 
									onfocus="disposeAlert('retypePassword');">
								<div id="retypePasswordError" class="invalid-feedback"></div>
							</div>
							<button class="btn btn-primary" type="submit">change password</button>
							<input type="hidden" name="_csrf" value="${_csrf.token}" />
						</form>
   					</div>
       	 		<hr>
       	 			<button class="btn btn-link" data-toggle="collapse" data-target="#geleteAccount" aria-expanded="true" aria-controls="geleteAccount">
        				delete account
       	 			</button>
					<!-- Collapse menue -->
	   				<div id="geleteAccount" class="collapse <#if currentPasswordError??>show</#if>" aria-labelledby="headingOne" data-parent="#accordion">
	   					<div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
							<strong>Warning!</strong> After this action your account will be permanently deleted!
						  	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						    	<span aria-hidden="true">&times;</span>
						  	</button>
						</div>
		    			<form method="post" action="/profile/settings/deleteAccount">
							<div class="form-group mt-3">
							    <label>Current Password:</label>
								<input class="form-control mb-2 ${(currentPasswordError??)?string('is-invalid', '')}" type="text" 
									name="currentPassword" id="currentPassword" placeholder="enter current password" 
									onfocus="disposeAlert('currentPassword');">
								<div id="currentPasswordError" class="invalid-feedback"><#if currentPasswordError??>${currentPasswordError}</#if></div>
							</div>
							<button class="btn btn-primary" type="submit">delete account</button>
							<input type="hidden" name="_csrf" value="${_csrf.token}" />
						</form>
	   				</div>
			</div>
		</div>
</div>
<script src="/customJs/formValidate.js"></script>

</@shell.htmlPage>