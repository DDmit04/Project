<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage> 

<#include "/parts/security.ftl">

<div class="card shadow col-8">
	<div class="card-body">
		<div class="text-center card-text">
			<form method="post" enctype="multipart/form-data" onsubmit="return validateChatCreate()">
				<div class="form-group mt-3">
					<input id="chatName" name="chatName" class="form-control col-mt ${(chatNameError??)?string('is-invalid', '')}" type="text" 
						   placeholder="chat name" value="${chatName?ifExists}" onfocus="disposeAlert('chatName')"> 
					<div id="chatNameError" class="invalid-feedback"><#if chatNameError??>${chatNameError}</#if></div>
					<input id="chatTitle" name="chatTitle" class="form-control mt-2" type="text" placeholder="chat title (optional)">
					<div class="input-group mt-2">
				  		<div class="custom-file">
				    		<input type="file" name="file" class="custom-file-input" id="postFile" aria-describedby="inputGroupFileAddon03"  >
				    		<label class="custom-file-label text-left" for="postFile">Chose chat pic (optional)</label>
				  		</div>
					</div>
				</div>
				<input type="hidden" name="_csrf" value="${_csrf.token}" />
				<button class="btn btn-primary mt-2" type="submit">create chat</button>
			</form>	
		</div>
	</div>
</div>
<script src="/customJs/formValidate.js"></script>
<script src="/customJs/checkFilename.js"></script>

</@shell.htmlPage>