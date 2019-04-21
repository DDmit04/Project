<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage>

<#include "parts/security.ftl">

<#if profileSettingsAttention??>
	<div class="alert alert-warning col-8" role="alert">
		${profileSettingsAttention}
	</div>
</#if>

<div class="col-8">
	<#if redirectMessage?? && redirectMessage != "">
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
  	 		<div id="changePassword" class="collapse <#if currentPasswordError != ''>show</#if> ml-2" aria-labelledby="headingOne" data-parent="#accordion">
    			<form method="post" action="/${currentUser.id}/profile/settings/changePassword" onsubmit="return validatePasswords();">
					<div class="form-group mt-3">
					    <label>Current Password:</label>
						<input class="form-control mb-2 ${(currentPasswordError??)?string('is-invalid', '')}" type="text" 
							name="currentPassword" id="currentPassword" placeholder="enter current password" 
							onfocus="disposeAlert('currentPassword');">
						<div id="currentPasswordError" class="invalid-feedback"><#if currentPasswordError != '' >${currentPasswordError}</#if></div>
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
			<button class="btn btn-link" data-toggle="collapse" data-target="#changeEmail" aria-expanded="true" aria-controls="changeEmail">
    			changeEmail
   	 		</button>
			<!-- Collapse menue -->
			<div id="changeEmail" class="collapse <#if changeEmailCodeError != '' || changeEmailError != '' || codeSended??>show</#if> ml-2" aria-labelledby="headingOne" data-parent="#accordion">
			    <form method="post" action="/${currentUser.id}/profile/settings/changeEmail" onsubmit="return validateChangeEmail();">
			    	<div class="form-group row">
					    <label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
					    <div class="col-sm-10">
					    	<input type="text" readonly class="form-control-plaintext" value="${currentEmail}">
					    	<#if emailConfirmed>
					    		<span class="badge badge-success">confirmed</span>
					    	<#else>
					    		<span class="badge badge-danger">not confirmed (visit your email)</span>
					    	</#if>
					    </div>
			 		</div>
			    	<label>change email:</label>
					<div class="form-group form-inline">
						<input  class="form-control mr-2 ${(chandeEmailError??)?string('is-invalid', '')}" type="text" 
							name="changeEmailCode" id="changeEmailCode" placeholder="enter code" 
							onfocus="disposeAlert('changeEmailCode');">
						<a id="sendCodeButton" role="button" class="btn btn-primary" href="/${currentUser.id}/profile/settings/sendCode">get code</a>
						<div id="changeEmailCodeError" class="invalid-feedback"><#if changeEmailCodeError != ''>${changeEmailCodeError}</#if></div>
					</div>
					<div class="form-group">
						<input class="form-control mb-2 ${(newEmailError??)?string('is-invalid', '')}" type="text" 
							value="${newEmail?ifExists}" name="newEmail" id="changeEmai" placeholder="new email" 
						    onfocus="disposeAlert('changeEmai');">
						<div id="changeEmailError" class="invalid-feedback"><#if changeEmailError != ''>${changeEmailError}</#if></div>
					</div>
					<button id="changeEmailButton" class="btn btn-primary" type="submit">change email</button>
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				</form>
				
			</div>
   	 		<hr>
   	 		<button class="btn btn-link" data-toggle="collapse" data-target="#geleteAccount" aria-expanded="true" aria-controls="geleteAccount">
    			delete account
   	 		</button>
				<!-- Collapse menue -->
			<div id="geleteAccount" class="collapse <#if accountDeleteError != ''>show</#if> ml-2" aria-labelledby="headingOne" data-parent="#accordion">
				<div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
					<strong>Warning!</strong> After this action your account will be permanently deleted!
				  	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    	<span aria-hidden="true">&times;</span>
				  	</button>
				</div>
	  			<form method="post" action="/${currentUser.id}/profile/settings/deleteAccount">
					<div class="form-group mt-3">
					    <label>Current Password:</label>
						<input class="form-control mb-2 ${(accountDeleteError??)?string('is-invalid', '')}" type="text" 
							name="accountDeletePassword" id="accountDeletePassword" placeholder="enter current password" 
							onfocus="disposeAlert('accountDeletePassword');">
						<div id="currentPasswordError" class="invalid-feedback"><#if accountDeleteError != ''>${accountDeleteError}</#if></div>
					</div>
					<button class="btn btn-primary" type="submit">delete account</button>
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	var changeEmailCode = document.getElementById('changeEmailCode');
	var changeEmailButton = document.getElementById('changeEmailButton');
	var sendCodeButton = document.getElementById('sendCodeButton')
	var emailIsConfirmed = ${emailConfirmed?c};
	if(!emailIsConfirmed) {
		changeEmailCode.disabled = true;
		changeEmailButton.disabled = true;
		sendCodeButton.classList.add('disabled');
	} else {
		changeEmailCode.disabled = false;
		changeEmailButton.disabled = false;
		sendCodeButton.classList.remove('disabled');
	}
</script>
<script src="/customJs/formValidate.js"></script>

</@shell.htmlPage>