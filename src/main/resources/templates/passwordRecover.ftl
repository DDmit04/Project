<#import "parts/HTMLshell.ftl" as shell>

<@shell.htmlPage>

<#if passwordRecoverAttention??>
	<div class="alert alert-warning col-8" role="alert">
		${passwordRecoverAttention}
	</div>
</#if>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="card col-md-5 shadow">
			<div class="card-body">
				<div class="text-center card-text">
					<h1 class="h3 mb-3 font-weight-normal">Password recover</h1>
				</div>
				<form method="post" action="/passwordRecover/recoverData">
					<div class="form-group">
					    <label>Username or email:</label>
						<input class="form-control mt-2 ${(recoverDataError??)?string('is-invalid', '')}" type="text" 
							name="recoverData" id="recoverData" placeholder="enter username or email" 
							value="${(recoverData)?ifExists}" onfocus="disposeAlert('recoverData');">
						<div id="recoverDataError" class="invalid-feedback"><#if recoverDataError??>${recoverDataError}</#if></div>
						<button id="sendCodeButton" type="submit" class="btn btn-primary mt-2">send code</button>
						<button id="changeUserButton" type="submit" class="btn btn-primary mt-2" onclick="changeUser();">change user</button>
					</div>
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				</form>
				<hr>
				<form id="passwordRecoverSt2" method="post" action="/passwordRecover/newPassword" onsubmit="return validatePasswords();">
					<div class="form-group form-inline">
						<input  class="form-control mr-2 ${(emailCodeError??)?string('is-invalid', '')}" type="text" 
							name="emailCode" id="emailCode" placeholder="enter code" 
							onfocus="disposeAlert('emailCode');">
						<button type="button" id="sendAgainCodeButton" class="btn btn-primary" href="/passwordRecover/sendCode">send again</button>
						<div id="emailCodeError" class="invalid-feedback"><#if emailCodeError??>${emailCodeError}</#if></div>
					</div>
					<div class="form-group">
					    <label>Passowd:</label>
						<input name="password" id="password" class="form-control mb-2 ${(passwordError??)?string('is-invalid', '')}" type="password" placeholder="enter password" 
							onfocus="disposeAlert('password');">
						<div id="passwordError" class="invalid-feedback"><#if passwordError??>${passwordError}</#if></div>
					</div>
					<div class="form-group">
					    <label>Retype passowd:</label>
						<input name="retypePassword" id="retypePassword" class="form-control mb-2" type="password" placeholder="enter retype password" 
							onfocus="disposeAlert('retypePassword');">
						<div id="retypePasswordError" class="invalid-feedback"></div>
					</div>
					<button type="submit" class="btn btn-primary">change password</button>
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				</form>
			</div>
		</div>
	<div class="col-md-6"></div>
	</div>
</div>

<script src="/customJs/formValidate.js"></script>
<script>
	var userExists = ${(user??)?c}
	var recoverData = document.getElementById("recoverData");
	var sendCodeButton = document.getElementById("sendCodeButton");
	var changeUserButton = document.getElementById("changeUserButton");
	var recoverSt2 = document.getElementById("passwordRecoverSt2");
	var St2Inputs = recoverSt2.getElementsByTagName("input");
	var St2Buttons = recoverSt2.getElementsByTagName("button");
	changeUserButton.disabled = true;
	muteSt2();
	if(userExists) {
		recoverData.disabled = true;
		sendCodeButton.disabled = true;
		changeUserButton.disabled = false;
		unmuteSt2();
	}
	function changeUser() {
		changeUserButton.disabled = true;
		recoverData.disabled = false;
		sendCodeButton.disabled = false;
		muteSt2();
	}
	function muteSt2() {
		for (var i = 0; i < St2Buttons.length; i++) {
			St2Buttons[i].disabled = true;
		}
		for (var i = 0; i < St2Inputs.length; i++) {
			St2Inputs[i].disabled = true;
		}
	}
	function unmuteSt2() {
		for (var i = 0; i < St2Buttons.length; i++) {
			St2Buttons[i].disabled = false;
		}
		for (var i = 0; i < St2Inputs.length; i++) {
			St2Inputs[i].disabled = false;
		}
	}
</script>
</@shell.htmlPage>