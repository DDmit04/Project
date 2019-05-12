<form method="post" enctype="multipart/form-data">
	<div class="form-group form-inline">
		<div class="input-group">
	  		<div class="custom-file">
	    		<input type="file" name="file" class="custom-file-input" id="postFile">
	    		<label class="custom-file-label text-right" for="postFile">upload pic</label>
	  		</div>
		</div>
		<button type="submit" class="btn btn-primary ml-2">upload image</button>
		<a role="button" href="${backPath}" class="btn btn-primary ml-2">back</a>
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

<script src="/customJs/checkFilename.js"></script>

