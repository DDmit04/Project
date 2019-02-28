<form method="post" enctype="multipart/form-data" action="" onsubmit="return validateComment()">
	<input id="commentText" class="form-control col-mt" type="text" name="commentText" placeholder="comment..." value="<#if editedComment??>${comment.commentText}</#if>"
		onfocus="disposeAlert('commentText')"> 
	<div id="commentTextError" class="invalid-feedback"></div>
	<div class="input-group mt-2">
	  	<div class="custom-file">
	    	<input type="file" name="commentPic" class="custom-file-input" id="commentPic">
	    	<label class="custom-file-label" for="postFile">Choose file</label>
	  	</div>
	</div>
	<button class="btn btn-primary mt-2" type="submit">
		<#if editedComment??>
			edit
		<#else>
			add
		</#if>
	</button>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>	
<script src="/customJs/formValidate.js"></script>
<script src="/customJs/checkFilename.js"></script>