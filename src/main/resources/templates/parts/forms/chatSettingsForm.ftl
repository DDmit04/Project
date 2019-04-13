<!-- uses in chatSettings -->

<form method="post" enctype="multipart/form-data" onsubmit="return validateChatCreate()">
	<div class="form-group mt-3">
		<div class="input-group mt-2">
	  		<div class="custom-file">
	    		<input type="file" name="file" class="custom-file-input" id="postFile" 
	    			aria-describedby="inputGroupFileAddon03" value="${chatPicName?ifExists}" >
	    		<label class="custom-file-label text-left" for="postFile">change chat pic</label>
	  		</div>
		</div>
		<label class="mt-2">chat name:</label>
		<input id="chatName" name="chatName" class="form-control col-mt ${(chatNameError??)?string('is-invalid', '')}" type="text" 
			   placeholder="chat name" value="${chatName?ifExists}" onfocus="disposeAlert('chatName')"> 
		<div id="chatNameError" class="invalid-feedback"><#if chatNameError??>${chatNameError}</#if></div>
		<label class="mt-2">chat title:</label>
		<input id="chatTitle" name="chatTitle" class="form-control" type="text" 
			placeholder="chat title (optional)" value="${chatTitle?ifExists}">
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button class="btn btn-primary mt-2" type="submit">save changes</button>
</form>

<script src="/customJs/formValidate.js"></script>
<script src="/customJs/checkFilename.js"></script>