<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#include "/parts/security.ftl">

	<form method="post" enctype="multipart/form-data" action="" onsubmit="return validatePost()">
		<input id="postText" class="form-control col-mt" type="text" name="groupName" placeholder="add text"
			onfocus="disposeAlert('postText')"> 
		<div id="postTextError" class="invalid-feedback"></div>
		<input class="form-control mt-2" type="text" name="groupInformation" id="tags" placeholder="add tags">
<!-- 		<div class="input-group mt-2"> -->
<!-- 	  		<div class="custom-file"> -->
<!-- 	    		<input type="file" name="file" class="custom-file-input" id="postFile" aria-describedby="inputGroupFileAddon03" > -->
<!-- 	    		<label class="custom-file-label" for="postFile">Choose file</label> -->
<!-- 	  		</div> -->
<!-- 		</div> -->
		<button class="btn btn-primary mt-2" type="submit">add</button>
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
	</form>	
	<script src="/customJs/formValidate.js"></script>
	<script src="/customJs/checkFilename.js"></script>

</@shell.htmlPage>