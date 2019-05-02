<!-- uses in chatSettings -->

<form method="post" enctype="multipart/form-data">
	<div class="form-group mt-3">
		<div class="input-group mt-2">
	  		<div class="custom-file">
	    		<input type="file" name="file" class="custom-file-input" id="postFile" 
	    			aria-describedby="inputGroupFileAddon03" value="${group.groupPicName?ifExists}" >
	    		<label class="custom-file-label text-left" for="postFile">change pic</label>
	  		</div>
		</div>
		<label class="mt-2">group title:</label>
		<input id="groupTitle" name="groupTitle" class="form-control col-mt" type="text" 
			   placeholder="group title" value="${group.groupTitle?ifExists}"> 
		<label class="mt-2">group infirmation:</label>
		<input id="groupInformation" name="groupInformation" class="form-control" type="text" 
			placeholder="group information" rows="3" value="${group.groupInformation?ifExists}">
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button class="btn btn-primary mt-2" type="submit">save changes</button>
</form>

<script src="/customJs/checkFilename.js"></script>