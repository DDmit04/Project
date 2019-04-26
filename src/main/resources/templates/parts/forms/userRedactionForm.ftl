<!-- uses in chatSettings -->

<form method="post" enctype="multipart/form-data">
	<div class="form-group mt-3">
		<div class="input-group mt-2">
	  		<div class="custom-file">
	    		<input type="file" name="file" class="custom-file-input" id="postFile" 
	    			aria-describedby="inputGroupFileAddon03" value="${user.userPicName?ifExists}" >
	    		<label class="custom-file-label text-left" for="postFile">change pic</label>
	  		</div>
		</div>
		<label class="mt-2">status:</label>
		<input id="userStatus" name="userStatus" class="form-control col-mt ${(chatNameError??)?string('is-invalid', '')}" type="text" 
			   placeholder="your status" value="${user.userStatus?ifExists}"> 
		<label class="mt-2">about you:</label>
		<input id="userInformation" name="userInformation" class="form-control" type="text" 
			placeholder="tell about yourself" rows="3" value="${user.userInformation?ifExists}">
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button class="btn btn-primary mt-2" type="submit">save changes</button>
</form>

<script src="/customJs/checkFilename.js"></script>